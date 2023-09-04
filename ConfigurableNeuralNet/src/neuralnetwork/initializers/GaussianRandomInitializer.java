package neuralnetwork.initializers;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Neuron;

public class GaussianRandomInitializer extends BaseInitializer {

    private final double mean;
    private final double stdDev;

    public GaussianRandomInitializer(double mean, double stdDev) {
        this.mean = mean;
        this.stdDev = stdDev;
    }

    @Override
    public void initializeBias(Neuron neuron) {
        double randomBias = mean + stdDev * Math.random(); // This is a simple representation; a proper Gaussian random generator should be used.
        neuron.setBias(randomBias);
    }

    @Override
    public void initializeWeight(Connection connection) {
        double randomWeight = mean + stdDev * Math.random(); // As above
        connection.setWeight(randomWeight);
    }
}