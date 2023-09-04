package neuralnetwork.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import neuralnetwork.functions.activation.ActivationFunction;
import neuralnetwork.initializers.UniformRandomInitializer;
import neuralnetwork.optimizers.Optimizer;

public class Layer implements Cloneable {

    private List<Neuron> neurons;

    public Layer(int numberOfNeurons, ActivationFunction activationFunction) {
        this.neurons = new ArrayList<>();
        for (int i = 0; i < numberOfNeurons; i++) {
        	Neuron neuron = new Neuron(activationFunction);
            this.neurons.add(neuron);
        }
    }

    /**
     * Connects this layer to another layer.
     * Each neuron in this layer will be connected to every neuron in the next layer.
     */
    public void connectTo(Layer nextLayer) {
        for (Neuron fromNeuron : this.neurons) {
            for (Neuron toNeuron : nextLayer.getNeurons()) {
                Connection.connect(fromNeuron, toNeuron);
            }
        }
    }

    // Overloaded method for custom weight and bias initialization
    public void connectTo(Layer nextLayer, UniformRandomInitializer initializer) {
        for (Neuron fromNeuron : this.neurons) {
            for (Neuron toNeuron : nextLayer.getNeurons()) {
                Connection connection = Connection.connect(fromNeuron, toNeuron);
                initializer.initializeWeight(connection);
            }
            initializer.initializeBias(fromNeuron);
        }
    }

    /**
     * Forward pass for this layer. Calculates the output for each neuron.
     */
    public void forwardPass() {
        for (Neuron neuron : neurons) {
            neuron.calculateOutput();
        }
    }
	
	public double[] getOutput() {
	    List<Neuron> outputNeurons = this.getNeurons();
	    
	    double[] outputs = new double[outputNeurons.size()];
	    for (int i = 0; i < outputNeurons.size(); i++) {
	        outputs[i] = outputNeurons.get(i).getOutput();
	    }
	    
	    return outputs;
	}
	
	/**
	 * Propagates the delta (previously error) to the neurons in this layer based on the deltas from the next layer.
	 */
	public void propagateDeltaFromNextLayer(Layer nextLayer) {
	    for (Neuron prevNeuron : this.getNeurons()) {
	        prevNeuron.calculateDeltaFromOutputConnections();
	    }
	}
	
	public Neuron getNeuronById(int id) {
	    for (Neuron neuron : neurons) {
	        if (neuron.id == id) {
	            return neuron;
	        }
	    }
	    return null;
	}


    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Layer{\n");
        for (Neuron neuron : neurons) {
            sb.append("\t").append(neuron.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Layer clone() {
        try {
            Layer cloned = (Layer) super.clone();
            cloned.neurons = neurons.stream()
                .map(Neuron::clone)
                .collect(Collectors.toList());
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone Layer object", e);
        }
    }

    // Getters and Setters

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(List<Neuron> neurons) {
        this.neurons = neurons;
    }
}
