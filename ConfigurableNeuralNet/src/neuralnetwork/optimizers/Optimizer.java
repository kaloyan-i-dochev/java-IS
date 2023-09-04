package neuralnetwork.optimizers;

import neuralnetwork.core.Neuron;
import neuralnetwork.functions.regularization.Regularized;
import neuralnetwork.utils.Describable;

public interface Optimizer extends Describable, Regularized {
    void updateWeights(Neuron neuron, double learningRate);
    void updateBias(Neuron neuron, double learningRate);
}
