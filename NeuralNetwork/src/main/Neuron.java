package main;

/**
 * Represents a single neuron in a neural network.
 * 
 * <p>
 * A neuron takes multiple inputs, applies weights to them, adds a bias, and
 * then passes the result through an activation function to produce an output.
 * </p>
 */
public class Neuron {
	private static int globalID = 0;
	private final int id;

	/** Last set of inputs passed to the neuron during activation. */
	private double[] lastInputs;

	/** Weights for each input. */
	private double[] weights;

	/** Bias value for the neuron. */
	private double bias;

	/** Output value after applying the activation function. */
	private double output;

	/** Delta value used during backpropagation. */
	private double delta;
	private double error;

	public Neuron(double[] weights, double bias) {
		this.weights = weights;
		this.bias = bias;
		id = globalID++;
	}

	/**
	 * Constructor for the Neuron class.
	 * 
	 * @param numInputs Number of inputs the neuron will receive.
	 */
	public Neuron(int numInputs) {
		this.weights = new double[numInputs];
		this.bias = Math.random(); // Initialize with a random value for now
//		this.bias = 0;

		// Initialize weights with random values for now
		for (int i = 0; i < numInputs; i++) {
			this.weights[i] = Math.random() - 0.5;
//			this.weights[i] = Math.random();
//			this.weights[i] = 0;
		}

		id = globalID++;
	}

	public static Neuron createPassThroughNeuron(int numInputs) {
		double[] weights = new double[numInputs];
		for (int i = 0; i < numInputs; i++) {
			weights[i] = 1.0; // Set weight to 1 for pass-through behavior
		}
		double bias = 0.0; // No bias for pass-through behavior
		return new Neuron(weights, bias);
	}

	private static final String ACTIVATION_FUNCTION = "Swish"; // Change this to switch functions

	/**
	 * Computes the weighted sum of inputs and applies the activation function.
	 * 
	 * @param inputs Input values.
	 * @return Output after activation.
	 */
	public double activate(double[] inputs) {
		this.lastInputs = inputs.clone();
		double sum = computeWeightedSum(inputs);

		switch (ACTIVATION_FUNCTION) {
		case "Sigmoid":
			this.output = 1.0 / (1.0 + Math.exp(-sum));
			break;
		case "ReLU":
			this.output = Math.max(0, sum);
			break;
		case "LeakyReLU":
			this.output = sum > 0 ? sum : 0.01 * sum; // 0.01 is the leaky constant
			break;
		case "Tanh":
			this.output = Math.tanh(sum);
			break;
		case "Swish":
			this.output = sum / (1 + Math.exp(-sum));
			break;
		default:
			throw new IllegalArgumentException("Invalid activation function");
		}
		return this.output;
	}

	public double computeWeightedSum(double[] inputs) {
		double sum = 0.0;
		for (int i = 0; i < inputs.length; i++) {
			sum += inputs[i] * weights[i];
		}
		sum += bias;
		return sum;
	}
	
    public void updateWeightsAndBias(double error, double learningRate) {
        // Calculate the gradient based on the activation function
        double gradient = this.output - error; // This is for sigmoid with cross-entropy loss

        // Update weights
        for (int weightIdx = 0; weightIdx < this.weights.length; weightIdx++) {
            double newWeight = this.weights[weightIdx] - learningRate * gradient * this.lastInputs[weightIdx];
            this.weights[weightIdx] = newWeight;
        }

        // Update bias
        this.bias -= learningRate * gradient;
    }
    
    public double calculateOutputError(double expectedOutput) {
        double y = expectedOutput; // true label
        double p = this.getOutput(); // predicted probability
        this.error = p - y; // gradient of the binary cross-entropy loss with sigmoid activation
        return this.error;
    }

    public void calculateDelta(double error) {
        this.delta = error * this.getOutput() * (1 - this.getOutput()); // Using sigmoid derivative
    }

    
	public double[] getLastInputs() {
		return lastInputs;
	}

	public void setLastInputs(double[] lastInputs) {
		this.lastInputs = lastInputs;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	// Getter and setter methods for weights, bias, output, and delta can be added
	// as needed.

	// Additional methods for backpropagation and weight updates can be added later.
}
