package neuralnetwork.core;

import neuralnetwork.functions.activation.ActivationFunction;
import neuralnetwork.functions.loss.LossFunction;
//import neuralnetwork.optimizers.Optimizer;
import neuralnetwork.initializers.UniformRandomInitializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Network implements Cloneable {

	private List<Layer> layers;
	private LossFunction lossFunction;
//    private Optimizer optimizer;

	public Network(LossFunction lossFunction) {
		this.layers = new ArrayList<>();
		this.lossFunction = lossFunction;
//        this.optimizer = optimizer;
	}

	public static Network simpleFeedForward(int[] layout, LossFunction lossFunction,
			ActivationFunction activationFunction, UniformRandomInitializer initializer) {
		Neuron.resetIds();
		Network res = new Network(lossFunction);
		for (int i = 0; i < layout.length; i++) {
			Layer layer = new Layer(layout[i], activationFunction);
			res.addLayer(layer, initializer);
		}
		for (Neuron n : res.getInputLayer().getNeurons()) {
			n.setBias(0);
		}
		return res;
	}

	/**
	 * Adds a layer to the network.
	 */
	public void addLayer(Layer layer, UniformRandomInitializer initializer) {
		// If there's already at least one layer in the network, connect the new layer
		// to the last layer
		if (!layers.isEmpty()) {
			getLastLayer().connectTo(layer, initializer);
		}
		layers.add(layer);
	}

	public Layer getInputLayer() {
		return layers.get(0);
	}

	public Layer getLastLayer() {
		return layers.get(layers.size() - 1);
	}

	/**
	 * Performs a forward pass through the entire network.
	 */
	public void forwardPass(double[] input) {
		if (layers.isEmpty()) {
			throw new IllegalStateException("Network has no layers.");
		}

		// Set input to the first layer
		List<Neuron> inputNeurons = layers.get(0).getNeurons();
		for (int i = 0; i < input.length; i++) {
			inputNeurons.get(i).setInput(input[i]);
		}

		// Propagate the data through the rest of the layers
		for (Layer layer : layers) {
			layer.forwardPass();
		}
	}

	public void backwardPass(double[] deltas) {
	    Layer outputLayer = getLastLayer();
	    List<Neuron> outputNeurons = outputLayer.getNeurons();
	    for (int i = 0; i < outputNeurons.size(); i++) {
	        Neuron neuron = outputNeurons.get(i);
	        neuron.setDelta(deltas[i]);
	    }

	    // Continue with the propagation of the delta
	    for (int i = layers.size() - 1; i > 0; i--) {
	        Layer layer = layers.get(i);
//	        if (i > 0) {
	            Layer previousLayer = layers.get(i - 1);
	            previousLayer.propagateDeltaFromNextLayer(layer);
//	        }
	    }
	}



	/**
	 * Performs a forward pass with the given input and returns the output of the
	 * network.
	 * 
	 * @param input The input values.
	 * @return The output values of the network.
	 */
	public List<Double> predict(double[] input) {
		forwardPass(input);
		return Arrays.stream(getOutput()) // Convert the array to a stream
				.boxed() // Box the primitive doubles to Double objects
				.collect(Collectors.toList()); // Collect the stream into a list
	}	

	public double[] getOutput() {
		return getLastLayer().getOutput();
	}

	/**
	 * Calculates the loss for the given actual and predicted values using the
	 * network's loss function.
	 */
	public double calculateLoss(double[] actual, double[] predicted) {
		return lossFunction.compute(actual, predicted);
	}
	
	/**
     * Returns all the weights in the network.
     * 
     * @return A list of weights from all connections in the network.
     */
    public List<Double> getAllWeights() {
        List<Double> weights = new ArrayList<>();
        
        for (Layer layer : layers) {
            for (Neuron neuron : layer.getNeurons()) {
                for (Connection connection : neuron.getInputConnections()) {
                    weights.add(connection.getWeight());
                }
            }
        }
        
        return weights;
    }
    
    @Override
    public Network clone() {
        try {
            Network cloned = (Network) super.clone();

            // Step 1: Clone Layers and Neurons
            cloned.layers = new ArrayList<>();
            for (Layer layer : layers) {
                cloned.layers.add(layer.clone());
            }

            // Step 2: Reconnect Cloned Neurons
            for (int i = 1; i < cloned.layers.size(); i++) {
                Layer previousLayer = cloned.layers.get(i - 1);
                Layer currentLayer = cloned.layers.get(i);

                for (Neuron neuron : currentLayer.getNeurons()) {
                    for (Connection conn : neuron.getInputConnections()) {
                        Neuron clonedFromNeuron = previousLayer.getNeuronById(conn.getFromNeuron().id);
                        Neuron clonedToNeuron = neuron;  // Current neuron in the loop

                        Connection clonedConnection = conn.clone();
                        clonedConnection.setFromNeuron(clonedFromNeuron);
                        clonedConnection.setToNeuron(clonedToNeuron);

                        clonedFromNeuron.addOutputConnection(clonedConnection);
                        clonedToNeuron.addInputConnection(clonedConnection);
                    }
                }
            }

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone Network object", e);
        }
    }


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Network{\n");
		for (Layer layer : layers) {
			sb.append(layer.toString()).append("\n");
		}
		sb.append("}");
		return sb.toString();
	}

	public String shortToString() {
		StringBuilder sb = new StringBuilder();

		for (Layer layer : layers) {
			sb.append("["); // Start of layer
			for (Neuron neuron : layer.getNeurons()) {
				sb.append("{"); // Start of neuron
				sb.append("id:").append(neuron.id).append(", ");

				// Append weights
				sb.append("weights:[");
				for (Connection conn : neuron.getInputConnections()) {
					sb.append(String.format("%.2f", conn.getWeight())).append(", ");
				}
				if (!neuron.getInputConnections().isEmpty()) {
					sb.setLength(sb.length() - 2); // Remove trailing comma and space
				}
				sb.append("], ");

				// Append bias
				sb.append("bias:").append(String.format("%.2f", neuron.getBias()));

				sb.append("}, "); // End of neuron
			}
			sb.setLength(sb.length() - 2); // Remove trailing comma and space
			sb.append("] "); // End of layer
		}

		return sb.toString();
	}

	// Getters and Setters

	public List<Layer> getLayers() {
		return layers;
	}

	public LossFunction getLossFunction() {
		return lossFunction;
	}

	public void setLossFunction(LossFunction lossFunction) {
		this.lossFunction = lossFunction;
	}
}
