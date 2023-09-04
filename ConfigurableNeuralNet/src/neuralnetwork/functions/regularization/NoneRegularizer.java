package neuralnetwork.functions.regularization;

import neuralnetwork.core.Network;

public class NoneRegularizer implements Regularizer {
    @Override
    public double compute(Network network) {
        return 0.0; // No regularization
    }

    @Override
    public double computeDerivative(double weight) {
        return 0.0; // No regularization derivative
    }

    @Override
    public String getDescription() {
        return "No Regularization";
    }
}
