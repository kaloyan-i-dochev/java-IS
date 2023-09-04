package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a layer in a neural network.
 * 
 * <p>
 * A layer consists of multiple neurons and handles operations that involve all neurons in the layer, 
 * like forward and backward propagation.
 * </p>
 */
public class Layer {

    /** List of neurons in the layer. */
    private List<Neuron> neurons;
    
    public Layer() {
		// TODO Auto-generated constructor stub
	}
    
    /**
     * Constructor for the Layer class.
     * 
     * @param numNeurons Number of neurons in the layer.
     * @param numInputsPerNeuron Number of inputs each neuron will receive.
     */
    public Layer(int numNeurons, int numInputsPerNeuron) {
        this.neurons = new ArrayList<>();
        for (int i = 0; i < numNeurons; i++) {
            this.neurons.add(new Neuron(numInputsPerNeuron));
        }
    }

    public static Layer createInputLayer(int numNeurons) {
        Layer inputLayer = new Layer();
        inputLayer.neurons = new ArrayList<>();
        for (int i = 0; i < numNeurons; i++) {
            Neuron passThroughNeuron = Neuron.createPassThroughNeuron(numNeurons);
            inputLayer.neurons.add(passThroughNeuron);
        }
        return inputLayer;
    }

    /**
     * Performs forward propagation for all neurons in the layer.
     * 
     * @param inputs Input values for the layer.
     * @return List of outputs from all neurons.
     */
    public List<Double> forward(double[] inputs) {
        List<Double> outputs = new ArrayList<>();
        for (Neuron neuron : neurons) {
            outputs.add(neuron.activate(inputs));
        }
        return outputs;
    }
    
    public List<Double> calculateOutputErrors(double[] expectedOutputs) {
        List<Double> outputErrors = new ArrayList<>();
        for (int i = 0; i < expectedOutputs.length; i++) {
            Neuron outputNeuron = this.getNeurons().get(i);
            double error = outputNeuron.calculateOutputError(expectedOutputs[i]);
            outputErrors.add(error);
        }
        return outputErrors;
    }



    // Getter and setter methods for neurons can be added as needed.

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

    // Additional methods for backpropagation and other functionalities can be added later.
}
