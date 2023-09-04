package neuralnetwork.initializers;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Neuron;

public class UniformRandomInitializer extends BaseInitializer {

    private final double weightInitFactor;
    private final double weightInitRange;
    private final double biasInitFactor;
    private final double biasInitRange;

    // Constructor to set initialization values
    public UniformRandomInitializer(double weightInitFactor, double weightInitRange, double biasInitFactor, double biasInitRange) {
        this.weightInitFactor = weightInitFactor;
        this.weightInitRange = weightInitRange;
        this.biasInitFactor = biasInitFactor;
        this.biasInitRange = biasInitRange;
    }

    // Method to initialize a neuron's bias
    public void initializeBias(Neuron neuron) {
        double randomBias = Math.random() * biasInitRange - biasInitRange; 
        neuron.setBias(randomBias * biasInitFactor);
    }

    // Method to initialize a connection's weight
    public void initializeWeight(Connection connection) {
        double randomWeight = Math.random() * weightInitRange - weightInitRange; 
        connection.setWeight(randomWeight * weightInitFactor);
    }

    // Static method to get a default initializer
    public static UniformRandomInitializer defaultInitializer() {
        return new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05);
    }
    
    @Override
    public String getDescription() {
    	return super.getDescription()
    			+ " weightInitFactor=" + weightInitFactor
    			+ " weightInitRange=" + weightInitRange
    			+ " biasInitFactor=" + biasInitFactor
    			+ " biasInitRange=" + biasInitRange;
    }

    // ... other initialization strategies can be added similarly
}
