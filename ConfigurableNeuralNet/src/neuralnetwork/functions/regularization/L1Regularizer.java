package neuralnetwork.functions.regularization;

import neuralnetwork.core.Network;

public class L1Regularizer implements Regularizer {
    private final double lambda;

    public L1Regularizer(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double compute(Network network) {
        double sum = 0.0;
        for (double weight : network.getAllWeights()) {
            sum += Math.abs(weight);
        }
        return lambda * sum;
    }

    @Override
    public double computeDerivative(double weight) {
        return lambda * Math.signum(weight); // Derivative of L1 regularization
    }

    @Override
    public String getDescription() {
        return "L1 Regularization with lambda = " + lambda;
    }
}
