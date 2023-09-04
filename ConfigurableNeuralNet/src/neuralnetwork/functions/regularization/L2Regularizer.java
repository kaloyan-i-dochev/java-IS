package neuralnetwork.functions.regularization;

import neuralnetwork.core.Network;

public class L2Regularizer implements Regularizer {
    private final double lambda;

    /**
     *@param lambda sets up the value to be multiplied by weight for computeDerivative 
     */
    public L2Regularizer(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double compute(Network network) {
        double sum = 0.0;
        for (double weight : network.getAllWeights()) {
            sum += weight * weight;
        }
        return 0.5 * lambda * sum; // 0.5 is a common factor used in L2 regularization
    }

    @Override
    public double computeDerivative(double weight) {
        return lambda * weight; // Derivative of L2 regularization
    }

    @Override
    public String getDescription() {
        return "L2 Regularization with lambda = " + lambda;
    }
}
