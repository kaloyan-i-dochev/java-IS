package neuralnetwork.functions.regularization;

import neuralnetwork.core.Network;
import neuralnetwork.utils.Describable;

public interface Regularizer extends Describable {
    // Computes the regularization term for the loss
    double compute(Network network);

    // Computes the derivative of the regularization term with respect to a weight
    double computeDerivative(double weight);
}
