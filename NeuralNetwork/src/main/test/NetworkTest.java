package main.test;

import main.Network;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NetworkTest {

    @Test
    public void testInitialization() {
        Network network = new Network(new int[]{2, 3, 1}); // 3 layers: 2 neurons in input layer, 3 in hidden, 1 in output
        assertNotNull(network); // Ensure network is created
        assertEquals(3, network.getLayers().size()); // Ensure 3 layers are initialized
        assertEquals(2, network.getLayers().get(0).getNeurons().size()); // Ensure input layer has 2 neurons
        assertEquals(3, network.getLayers().get(1).getNeurons().size()); // Ensure hidden layer has 3 neurons
        assertEquals(1, network.getLayers().get(2).getNeurons().size()); // Ensure output layer has 1 neuron
    }

    @Test
    public void testForwardPropagation() {
        Network network = new Network(new int[]{2, 3, 1});
        var outputs = network.forwardPropagation(new double[]{0.5, 0.5});
        assertEquals(1, outputs.size()); // Ensure 1 output is produced (matching the output layer's neuron count)
    }

    @Test
    public void testTraining() {
        Network network = new Network(new int[]{2, 3, 1});
        double[][] trainingInputs = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        };
        double[][] trainingOutputs = {
            {0},
            {1},
            {1},
            {0}
        };
        assertDoesNotThrow(() -> network.train(trainingInputs, trainingOutputs, 1000)); // Ensure no exceptions during training
    }

    // ... Additional tests can be added as needed
}
