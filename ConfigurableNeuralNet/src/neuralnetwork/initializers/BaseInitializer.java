package neuralnetwork.initializers;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Neuron;
import neuralnetwork.utils.Describable;

public abstract class BaseInitializer implements Describable {
    public abstract void initializeBias(Neuron neuron);
    public abstract void initializeWeight(Connection connection);
}
