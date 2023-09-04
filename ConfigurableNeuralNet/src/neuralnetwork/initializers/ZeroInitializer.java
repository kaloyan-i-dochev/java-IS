package neuralnetwork.initializers;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Neuron;

public class ZeroInitializer extends BaseInitializer {

    @Override
    public void initializeBias(Neuron neuron) {
        neuron.setBias(0.0);
    }

    @Override
    public void initializeWeight(Connection connection) {
        connection.setWeight(0.0);
    }
}