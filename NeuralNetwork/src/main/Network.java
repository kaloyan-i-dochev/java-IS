package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a neural network consisting of multiple layers.
 */
public class Network {

	private List<Layer> layers;
	private double learningRate;

	/**
	 * Constructor for the Network class.
	 * 
	 * @param layerSizes An array where each element represents the number of
	 *                   neurons in that layer. The length of the array determines
	 *                   the number of layers.
	 */
	public Network(int[] layerSizes) {
		this.layers = new ArrayList<>();

		// Initialize the input layer
		this.layers.add(Layer.createInputLayer(layerSizes[0]));

		// Initialize the subsequent layers
		for (int i = 1; i < layerSizes.length; i++) {
			int numInputs = layerSizes[i - 1];
			this.layers.add(new Layer(layerSizes[i], numInputs));
		}
		this.learningRate = 0.001; // Default learning rate, can be adjusted
	}

	private double[] lastInputs; // Add this field to store the last set of inputs passed to the network

	public List<Double> forwardPropagation(double[] inputs) {
		this.lastInputs = inputs;
		for (Layer layer : layers) {
			lastOutputs = layer.forward(inputs);
			inputs = lastOutputs.stream().mapToDouble(Double::doubleValue).toArray();
		}
		return lastOutputs;
	}

	private List<Double> lastOutputs;

	public void backPropagation(double[] expectedOutput) {
		// Calculate the output error for the last layer
		List<Double> outputErrors = new ArrayList<>();
		for (int i = 0; i < expectedOutput.length; i++) {
			double error = expectedOutput[i] - lastOutputs.get(i);
			outputErrors.add(error);
		}

		// Propagate the error backward through the network
		for (int layerIdx = layers.size() - 1; layerIdx >= 1; layerIdx--) {
			Layer currentLayer = layers.get(layerIdx);
			List<Double> nextLayerErrors = new ArrayList<>();
			// Initialize nextLayerErrors with zeros
			for (int i = 0; i < currentLayer.getNeurons().get(0).getWeights().length; i++) {
				nextLayerErrors.add(0.0);
			}

			for (int neuronIdx = 0; neuronIdx < currentLayer.getNeurons().size(); neuronIdx++) {
				Neuron neuron = currentLayer.getNeurons().get(neuronIdx);
				double error = outputErrors.get(neuronIdx);
				neuron.setDelta(error * neuron.getOutput() * (1 - neuron.getOutput())); // Using sigmoid derivative

				// Calculate error for the next layer
				for (int weightIdx = 0; weightIdx < neuron.getWeights().length; weightIdx++) {
					double nextError = neuron.getWeights()[weightIdx] * error;
					nextLayerErrors.set(weightIdx, nextLayerErrors.get(weightIdx) + nextError);
				}

//				// Update weights and bias
				for (int weightIdx = 0; weightIdx < neuron.getWeights().length; weightIdx++) {
					double newWeight = neuron.getWeights()[weightIdx]
							+ learningRate * error * neuron.getLastInputs()[weightIdx];
					neuron.getWeights()[weightIdx] = newWeight;
				}
				neuron.setBias(neuron.getBias() + learningRate * error);
			}
			outputErrors = nextLayerErrors;
		}
	}

	private Layer outputLayer() {
		return layers.get(layers.size() - 1);
	}

	public void train(double[][] trainingInputs, double[][] trainingOutputs, int epochs) {
//		printNetworkState(0);
		for (int epoch = 0; epoch < epochs; epoch++) {
			for (int i = 0; i < trainingInputs.length; i++) {
				forwardPropagation(trainingInputs[i]);
				backPropagation(trainingOutputs[i]);
//				printNetworkState(epoch);
			}
		}
	}

	public List<Double> predict(double[] inputs) {
		return forwardPropagation(inputs);
	}

	public double calculateMeanSquareLoss(double[] expectedOutput, List<Double> actualOutput) {
		double sum = 0.0;
		for (int i = 0; i < expectedOutput.length; i++) {
			double error = expectedOutput[i] - actualOutput.get(i);
			sum += error * error;
		}
		return sum / expectedOutput.length; // Mean Squared Error
	}

//	public double binaryCrossEntropyLoss(double[] expectedOutputs, List<Double> actualOutputs) {
//	    double sum = 0.0;
//	    for (int i = 0; i < expectedOutputs.length; i++) {
//	        double y = expectedOutputs[i];
//	        double yHat = actualOutputs.get(i);
//	        sum += -y * Math.log(yHat) - (1 - y) * Math.log(1 - yHat);
//	    }
//	    return sum / expectedOutputs.length;
//	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void printNetworkState(int epoch) {
		StringBuilder state = new StringBuilder();

		// Add epoch number
		state.append("Epoch: ").append(epoch).append(" | ");

		// Iterate through each layer
		for (Layer layer : layers) {
			state.append("["); // Start of layer

			// Iterate through each neuron in the layer
			for (Neuron neuron : layer.getNeurons()) {
				state.append("{"); // Start of neuron data

				// Print weights
				double[] weights = neuron.getWeights();
				for (int i = 0; i < weights.length; i++) {
					state.append(String.format("%.2f", weights[i])); // Format to 2 decimal places
					if (i < weights.length - 1) {
						state.append(",");
					}
				}

				// Print bias
				state.append(";").append(String.format("%.2f", neuron.getBias()));

				state.append("}"); // End of neuron data

				// If not the last neuron, add a comma for separation
				if (neuron != layer.getNeurons().get(layer.getNeurons().size() - 1)) {
					state.append(", ");
				}
			}

			state.append("]"); // End of layer

			// If not the last layer, add a pipe for separation
			if (layer != layers.get(layers.size() - 1)) {
				state.append(" | ");
			}
		}

		// Print the constructed state
		System.out.println(state.toString());
	}

	// Getter and setter methods for layers and learningRate can be added as needed.
}
