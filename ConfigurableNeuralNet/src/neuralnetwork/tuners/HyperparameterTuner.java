package neuralnetwork.tuners;

import java.util.*;
import java.util.stream.Collectors;

import neuralnetwork.core.Network;
import neuralnetwork.functions.activation.ActivationFunction;
import neuralnetwork.functions.loss.LossFunction;
import neuralnetwork.functions.regularization.L1Regularizer;
import neuralnetwork.functions.regularization.L2Regularizer;
import neuralnetwork.functions.regularization.NoneRegularizer;
import neuralnetwork.functions.regularization.Regularizer;
import neuralnetwork.functions.termination.TargetAccuracyTermination;
import neuralnetwork.initializers.UniformRandomInitializer;
import neuralnetwork.metrics.MetricsLogger;
import neuralnetwork.metrics.TrainingMetrics;
import neuralnetwork.optimizers.GradientDescentOptimizer;
import neuralnetwork.optimizers.MomentumOptimizer;
import neuralnetwork.optimizers.Optimizer;
import neuralnetwork.training.ConstantLRScheduler;
import neuralnetwork.training.LearningRateScheduler;
import neuralnetwork.training.StepDecayScheduler;
import neuralnetwork.training.Trainer;
import neuralnetwork.utils.NetworkLayouts;
import neuralnetwork.utils.Tasks;

public class HyperparameterTuner {
	public static int currentCombinationNum = 0;
	public static int totalCombinations = 0;

	public static void main(String[] args) {
		// Parameter: Task
		List<Tasks> tasks = Arrays.asList(Tasks.XOR);

		// Parameter: NetworkLayout
		List<NetworkLayouts> layouts = Arrays.asList(NetworkLayouts.SIMPLE);

		// Parameter: cycles
		List<Integer> cyclesList = Arrays.asList(10, 20);

		// Parameter: epochs
		List<Integer> epochsList = Arrays.asList(1000, 2000, 5000);

		// Parameter: scheduler
		List<LearningRateScheduler> schedulers = new ArrayList<>();

		// For ConstantLRScheduler
		List<Double> constantLRSchedulersLearningRates = Arrays.asList(0.001, 0.01, 0.1);
		for (double lr : constantLRSchedulersLearningRates) {
			schedulers.add(new ConstantLRScheduler(lr));
		}

		// For StepDecayScheduler
//		List<Double> initialRates = Arrays.asList(0.1, 0.01, 0.001); // Example values
//		List<Double> decayRates = Arrays.asList(0.5, 0.9, 0.95);     // Example values
//		List<Integer> stepSizes = Arrays.asList(100, 200, 500);      // Example values
//
//		for (double initialRate : initialRates) {
//		    for (double decayRate : decayRates) {
//		        for (int stepSize : stepSizes) {
//		            schedulers.add(new StepDecayScheduler(initialRate, decayRate, stepSize));
//		        }
//		    }
//		}

		// Parameter: activationFunction
		List<ActivationFunction> activationFunctions = Arrays.asList(ActivationFunction.SIGMOID);

		// Parameter: lossFunction
		List<LossFunction> lossFunctions = Arrays.asList(LossFunction.MEAN_SQUARED_ERROR);

		// Parameter: initializer
		List<UniformRandomInitializer> initializers = new ArrayList<>();

		List<Double> weightInitFactors = Arrays.asList(0.5, 1.0, 1.5);
		List<Double> weightInitRanges = Arrays.asList(0.05, 0.1, 0.15);
		List<Double> biasInitFactors = Arrays.asList(0.5, 1.0, 1.5);
		List<Double> biasInitRanges = Arrays.asList(-0.05, 0.0, 0.05);

		for (double wFactor : weightInitFactors) {
			for (double wRange : weightInitRanges) {
				for (double bFactor : biasInitFactors) {
					for (double bRange : biasInitRanges) {
						initializers.add(new UniformRandomInitializer(wFactor, wRange, bFactor, bRange));
					}
				}
			}
		}

		// Parameter: regularizer
		List<Regularizer> regularizers = new ArrayList<>();

		List<Double> lambdas = Arrays.asList(0.001, 0.01, 0.1);

		// Add NoneRegularizer
		regularizers.add(new NoneRegularizer());

		for (double lambda : lambdas) {
			regularizers.add(new L1Regularizer(lambda));
			regularizers.add(new L2Regularizer(lambda));
		}

		// Parameter: optimizer
		List<Optimizer> optimizers = new ArrayList<>();

		// Add GradientDescentOptimizer for each regularizer
		for (Regularizer regularizer : regularizers) {
			optimizers.add(new GradientDescentOptimizer(regularizer));
		}

		// Add MomentumOptimizer for each combination of momentumCoefficient and
		// regularizer
		List<Double> momentumCoefficients = Arrays.asList(0.01, 0.05, 0.1, 0.25, 0.5, 0.7);
		for (double momentumCoefficient : momentumCoefficients) {
			for (Regularizer regularizer : regularizers) {
				optimizers.add(new MomentumOptimizer(momentumCoefficient, regularizer));
			}
		}

		totalCombinations = tasks.size() * layouts.size() * cyclesList.size() * epochsList.size() * schedulers.size()
				* activationFunctions.size() * lossFunctions.size() * initializers.size() * optimizers.size();

		System.out.println("Total combinations: " + totalCombinations);
		System.out.println("Tasks: " + tasks.size());
		System.out.println("Layouts: " + layouts.size());
		System.out.println("Cycles: " + cyclesList.size());
		System.out.println("Epochs: " + epochsList.size());
		System.out.println("Schedulers: " + schedulers.size());
		System.out.println("ActivationFunctions: " + activationFunctions.size());
		System.out.println("LossFunctions: " + lossFunctions.size());
		System.out.println("Initializers: " + initializers.size());
		System.out.println("Optimizers: " + optimizers.size());

		currentCombinationNum = 0;
		// @formatter:off
		for (Tasks task : tasks) {
		for (NetworkLayouts layout : layouts) {
		for (int cycles : cyclesList) {
		for (int epochs : epochsList) {
		for (LearningRateScheduler scheduler : schedulers) {
		for (ActivationFunction activationFunction : activationFunctions) {
		for (LossFunction lossFunction : lossFunctions) {
		for (UniformRandomInitializer initializer : initializers) {
		for (Optimizer optimizer : optimizers) {
			run(
				task,
				layout,
				cycles,
				epochs,
				scheduler,
				activationFunction,
				lossFunction,
				initializer,
				optimizer
			);
		}
		}
		}
		}
		}
		}
		}
		}
		}
		// @formatter:on
	}

	// @formatter:off
	public static void run(
			Tasks task, 
			NetworkLayouts layout, 
			int cycles, 
			int epochs,
			LearningRateScheduler scheduler, 
			ActivationFunction activationFunction, 
			LossFunction lossFunction,
			UniformRandomInitializer initializer, 
			Optimizer optimizer) {
		Network network = Network.simpleFeedForward(
			layout.getLayout(), 
			lossFunction, 
			activationFunction, 
			initializer
		);
		Trainer trainer = new Trainer(
			network,
			optimizer, 
			lossFunction, 
			scheduler, 
			optimizer.getRegularizer(), 
			new TargetAccuracyTermination(1.0)
		);
		// @formatter:on
//		System.out.println(network);
//		System.out.println(trainer);
		if (currentCombinationNum % 1000 == 0) 
			System.out.println(
					"Combination " + currentCombinationNum + " of " + totalCombinations + " total combinations.");
		currentCombinationNum++;
//		System.out.println("Testing operation: " + task.toString());
//		System.out.println("Training Scheduler: " + scheduler.getDescription());
//		System.out.println("Network layout: " + Arrays.toString(layout.getLayout()));
//		System.out.println("Optimizer: " + optimizer.getDescription());
//		System.out.println("Activation: " + activationFunction.getDescription());
//		System.out.println("Initializer: " + initializer.getDescription());
//		System.out.println("Loss Function: " + lossFunction.getDescription());
//		System.out.println("Training cycles: " + cycles + " | Epochs per session: " + epochs);
//		System.out.println("====================================");
		TrainingMetrics metr = trainer.train(task.getInputData(), task.getOutputData(), epochs, cycles);
		if(trainer.getLatestAccuracy() >= 1) {
			System.out.println(
					"Combination " + currentCombinationNum + " of " + totalCombinations + " total combinations.");
			System.out.println("Testing operation: " + task.toString());
			System.out.println("Training Scheduler: " + scheduler.getDescription());
			System.out.println("Network layout: " + Arrays.toString(layout.getLayout()));
			System.out.println("Optimizer: " + optimizer.getDescription());
			System.out.println("Activation: " + activationFunction.getDescription());
			System.out.println("Initializer: " + initializer.getDescription());
			System.out.println("Loss Function: " + lossFunction.getDescription());
			System.out.println("Training cycles: " + cycles + " | Epochs per session: " + epochs);
			System.out.println("====================================");
		}
//		System.out.println(trainer.getLatestAccuracy());
	}
}
