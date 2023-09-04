package neuralnetwork.core;

import java.util.Objects;

public class Connection implements Cloneable {
	private Neuron fromNeuron;
	private Neuron toNeuron;
	private double weight;
	private double deltaWeight; // Used for weight updates during backpropagation
	public final long id;
	public static long nextid = 0;

	public Connection(Neuron fromNeuron, Neuron toNeuron) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.weight = 1;
		this.deltaWeight = 0.0;
		id = nextid++;
	}

	public static Connection connect(Neuron fromNeuron, Neuron toNeuron) {
		Connection connection = new Connection(fromNeuron, toNeuron);
		connection.setWeight(toNeuron.getActivationFunction().initializeWeight());
		fromNeuron.addOutputConnection(connection);
		toNeuron.addInputConnection(connection);
		return connection;
	}

	@Override
	public String toString() {
		return "Connection{" + "fromNeuronId=" + fromNeuron.id + ", toNeuronId=" + toNeuron.id + ", weight=" + weight
				+ '}';
	}
	
    @Override
    public Connection clone() {
        try {
            Connection cloned = (Connection) super.clone();
            // Since Neurons are shared among connections, we don't deep clone them here.
            // They will be deep cloned in the Network's clone method.
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone Connection object", e);
        }
    }

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Connection that = (Connection) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	// Getters and Setters

	public Neuron getFromNeuron() {
		return fromNeuron;
	}

	public void setFromNeuron(Neuron fromNeuron) {
		this.fromNeuron = fromNeuron;
	}

	public Neuron getToNeuron() {
		return toNeuron;
	}

	public void setToNeuron(Neuron toNeuron) {
		this.toNeuron = toNeuron;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getDeltaWeight() {
		return deltaWeight;
	}

	public void setDeltaWeight(double deltaWeight) {
		this.deltaWeight = deltaWeight;
	}
}
