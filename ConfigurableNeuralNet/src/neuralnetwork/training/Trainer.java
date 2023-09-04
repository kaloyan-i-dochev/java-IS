package neuralnetwork.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Layer;
import neuralnetwork.core.Network;
import neuralnetwork.core.Neuron;
import neuralnetwork.functions.loss.LossFunction;
import neuralnetwork.functions.regularization.Regularizer;
import neuralnetwork.functions.termination.TerminationCondition;
import neuralnetwork.metrics.MetricsLogger;
import neuralnetwork.metrics.TrainingMetrics;
import neuralnetwork.optimizers.Optimizer;

public class Trainer {
	private static final boolean LOG_LAST_SESSION_RESULTS_OVERRIDE = false;
	private static final boolean LOG_LAST_EPOCH_RESULTS_OVERRIDE = true;
	private static final boolean LOG_SESSION_RESULTS = true;
	private static final boolean LOG_EPOCH_RESULTS = false;
	private static final boolean LOG_EARLY_TERMINATION = true;

	MetricsLogger logger = new MetricsLogger();

	private Network currentNetwork;
	private Optimizer optimizer;
	private Regularizer regularizer;
	private LossFunction lossFunction;
	private LearningRateScheduler scheduler;
	private List<PerformanceData> performanceDataList = new ArrayList<>();
	private double initialAccuracy = 0.0;
	private int currentTotalEpochs = 0;
	private TerminationCondition terminationCondition;

	public Trainer(Network network, Optimizer optimizer, LossFunction lossFunction, LearningRateScheduler scheduler,
			Regularizer regularizer, TerminationCondition terminationCondition) {
		this.currentNetwork = network;
		this.optimizer = optimizer;
		this.lossFunction = lossFunction;
		this.scheduler = scheduler;
		this.regularizer = regularizer;
		this.terminationCondition = terminationCondition;
	}

	public TrainingMetrics train(double[][] inputs, double[][] desiredOutputs, int epochs, int trainingCycles) {
		TrainingMetrics metrics = new TrainingMetrics();
		performanceDataList = new ArrayList<>();
		currentTotalEpochs = 0; // Reset for each session
		for (int trainingCycle = 0; trainingCycle <= trainingCycles; trainingCycle++) {
			for (int epoch = 0; epoch <= epochs; epoch++) {
				currentTotalEpochs++;
				double totalError = 0;
				double currentError = 0;
				double learningRate = scheduler.getLearningRate(epoch, epochs, trainingCycle, trainingCycles,
						currentError);

				for (int i = 0; i < inputs.length; i++) {
					double[] input = inputs[i];
					double[] desiredOutput = desiredOutputs[i];

					// Forward pass
					currentNetwork.forwardPass(input);

					// Calculate error
					double[] actualOutput = currentNetwork.getOutput();
					currentError = lossFunction.compute(desiredOutput, actualOutput);
					totalError += currentError;

					// Backward pass
					currentNetwork.backwardPass(computeOutputDelta(desiredOutput));

					for (int layerIndex = 1; layerIndex < currentNetwork.getLayers().size(); layerIndex++) {
						// Start from 1 to skip the input layer
						Layer layer = currentNetwork.getLayers().get(layerIndex);
						for (Neuron neuron : layer.getNeurons()) {
							optimizer.updateWeights(neuron, learningRate);
							optimizer.updateBias(neuron, learningRate);
						}
					}

					List<Double> temp = Arrays.stream(input).boxed().collect(Collectors.toList());
					metrics.logPrediction(epoch, temp, currentNetwork.predict(input));
					metrics.logError(epoch, currentError);
					metrics.logNetworkState(epoch, currentNetwork);
				}
				// Update metrics

				// Add regularization term to the total error
				totalError += regularizer.compute(currentNetwork);
				totalError /= inputs.length; // Average error

				if (LOG_EPOCH_RESULTS || (LOG_LAST_EPOCH_RESULTS_OVERRIDE && epoch == epochs)) {
					System.out.println("Epoch " + epoch + ": Error = " + totalError);
					System.out.println(currentNetwork.shortToString());
				}

				if (terminationCondition != null && terminationCondition.shouldTerminate(this)) {
					PerformanceData data = testPerformance(inputs, desiredOutputs);
					performanceDataList.add(data);
					if(LOG_EARLY_TERMINATION) {
					System.out.println("Accuracy for cycle " + (trainingCycle) + ": " + data.getAccuracy());
					System.out.println(data);
					System.out.println("Termination condition met. Stopping training at cycle: " + trainingCycle
							+ " epoch: " + epoch);
					}
//					PerformanceData data = testPerformance(inputs, desiredOutputs);
//					performanceDataList.add(data);
					return metrics; // Exit the training method
				}
			}
			PerformanceData data = testPerformance(inputs, desiredOutputs);
			performanceDataList.add(data);

			if (LOG_SESSION_RESULTS || (LOG_LAST_SESSION_RESULTS_OVERRIDE && trainingCycle == trainingCycles)) {
				System.out.println("Accuracy for cycle " + (trainingCycle) + ": " + data.getAccuracy());
				System.out.println(data);
				System.out.println("====================================");
				System.out.println();
			}
		}

		return metrics;
	}

	public double[] computeOutputDelta(double[] desiredOutput) {
		Layer outputLayer = currentNetwork.getLastLayer();
		List<Neuron> outputNeurons = outputLayer.getNeurons();
		double[] deltas = new double[outputNeurons.size()];
		for (int i = 0; i < outputNeurons.size(); i++) {
			Neuron neuron = outputNeurons.get(i);
			double derivative = lossFunction.computeDerivative(desiredOutput[i], neuron.getOutput());
			double delta = derivative * (desiredOutput[i] - neuron.getOutput()); // This is the gradient

			// For each incoming connection, compute the regularization term and adjust the
			// delta
			for (Connection conn : neuron.getInputConnections()) {
				double weight = conn.getWeight();
				double regularizationTerm = regularizer.computeDerivative(weight);
				delta += regularizationTerm; // Adjust the delta with the regularization term
			}

			deltas[i] = delta;
		}
		return deltas;
	}

	private PerformanceData testPerformance(double[][] trainingInputs, double[][] trainingOutputs) {
		PerformanceData data = new PerformanceData();
		int correctPredictions = 0;
		for (int j = 0; j < trainingInputs.length; j++) {
			List<Double> predictionList = currentNetwork.predict(trainingInputs[j]);
			double prediction = predictionList.get(0);
			if (isPredictionCorrect(predictionList, trainingOutputs[j])) {
				correctPredictions++;
			}

			// Logging and tracking predictions
			String inputKey = Arrays.toString(trainingInputs[j]);
			double lastPrediction = getLastPrediction(inputKey);
			double delta = prediction - lastPrediction;

			// Store the predictions, expected outputs, and deltas in the PerformanceData
			// instance
			data.getPredictions().put(inputKey, prediction);
			data.getExpectedOutputs().put(inputKey, trainingOutputs[j][0]); // Storing the expected output
			data.getDeltas().put(inputKey, delta);
		}

		double accuracy = (double) correctPredictions / trainingInputs.length;
		data.setAccuracy(accuracy);

		return data;
	}

	private double getLastPrediction(String inputKey) {
		if (performanceDataList.isEmpty()) {
			return 0.0; // or some default value
		}
		PerformanceData lastSessionData = performanceDataList.get(performanceDataList.size() - 1);
		return lastSessionData.getPredictions().getOrDefault(inputKey, 0.0);
	}

	private boolean isPredictionCorrect(List<Double> prediction, double[] expectedOutput) {
		// For this problem, we can use a threshold of 0.5 to determine the predicted
		// class
		return (prediction.get(0) < 0.5 && expectedOutput[0] == 0)
				|| (prediction.get(0) >= 0.5 && expectedOutput[0] == 1);
	}

	/**
	 * Returns the accuracy of the latest training session.
	 * 
	 * @return The latest accuracy, or 0.0 if no training sessions have been
	 *         completed.
	 */
	public double getLatestAccuracy() {
		if (performanceDataList.isEmpty()) {
			return 0.0;
		}
		PerformanceData lastSessionData = performanceDataList.get(performanceDataList.size() - 1);
		return lastSessionData.getAccuracy();
	}

	/**
	 * Returns the number of epochs completed in the current training session.
	 * 
	 * @return The current session's epoch count.
	 */
	public int getCurrentTotalEpochs() {
		return currentTotalEpochs;
	}
	// Additional methods for validation, early stopping, etc. can be added here.
}
