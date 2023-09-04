package neuralnetwork.core;

import neuralnetwork.functions.activation.ActivationFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Neuron implements Cloneable {

	private List<Connection> inputConnections;
	private List<Connection> outputConnections;
	private ActivationFunction activationFunction;
    private double input;
	private double output;
	private double delta;  // Renamed from error
	private double bias;
	private double deltaBias;
	public final int id;
	private static int nextId = 0;

	public Neuron(ActivationFunction activationFunction) {
		this.inputConnections = new ArrayList<>();
		this.outputConnections = new ArrayList<>();
		this.activationFunction = activationFunction;
		this.id = nextId++;
	}

	public double fire() {
		calculateOutput();
		return getOutput();
	}
	
    public void setInput(double input) {
        this.input = input;
    }

	public void calculateOutput() {
		if (inputConnections.isEmpty()) {
			this.output = activationFunction.activate(input + bias);
		} else {
			double sum = sumInputs();
			this.output = activationFunction.activate(sum);
		}
	}

	private double sumInputs() {
		double sum = 0.0;
		for (Connection conn : inputConnections) {
			sum += conn.getFromNeuron().getOutput() * conn.getWeight();
		}
		sum += bias;
		return sum;
	}

	public void calculateDeltaFromOutputConnections() {  // Renamed from calculateErrorFromOutputConnections
		double deltaSum = 0.0;
		for (Connection conn : this.getOutputConnections()) {
			deltaSum += conn.getWeight() * conn.getToNeuron().getDelta();  // Renamed getError to getDelta
		}
		this.setDelta(deltaSum);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Neuron{" + "id=" + id + ", output=" + output + ", delta=" + delta + ", activationFunction="
				+ activationFunction.getClass().getSimpleName() + ", inputConnections=[\n");
		for (Connection conn : inputConnections) {
			sb.append("\t\t").append(conn.toString()).append("\n");
		}
		sb.append("\t], outputConnections=[\n");
		for (Connection conn : outputConnections) {
			sb.append("\t\t").append(conn.toString()).append("\n");
		}
		sb.append("\t]}");
		return sb.toString();
	}

    @Override
    public Neuron clone() {
        try {
            Neuron cloned = (Neuron) super.clone();
            cloned.inputConnections = new ArrayList<>();
            cloned.outputConnections = new ArrayList<>();
//            cloned.inputConnections = inputConnections.stream()
//                .map(Connection::clone)
//                .collect(Collectors.toList());
//            cloned.outputConnections = outputConnections.stream()
//                .map(Connection::clone)
//                .collect(Collectors.toList());
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone Neuron object", e);
        }
    }
    
    public static int resetIds() {
    	int res = nextId;
    	nextId=0;
    	return res;
    }
	
	// Getters and Setters

	public double getOutput() {
		return output;
	}

	public void addInputConnection(Connection connection) {
		this.inputConnections.add(connection);
	}

	public void addOutputConnection(Connection connection) {
		this.outputConnections.add(connection);
	}

	public List<Connection> getInputConnections() {
		return inputConnections;
	}

	public List<Connection> getOutputConnections() {
		return outputConnections;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public double getDelta() {  // Renamed from getError
		return delta;
	}

	public void setDelta(double delta) {  // Renamed from setError
		this.delta = delta;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getDeltaBias() {
		return deltaBias;
	}

	public void setDeltaBias(double deltaBias) {
		this.deltaBias = deltaBias;
	}
}
