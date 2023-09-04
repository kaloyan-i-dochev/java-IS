package main.test;

import main.Layer;
import main.Neuron;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LayerTest {

    @Test
    public void testInitialization() {
        Layer layer = new Layer(3, 2); // 3 neurons, each with 2 inputs
        assertNotNull(layer); // Ensure layer is created
        assertEquals(3, layer.getNeurons().size()); // Ensure 3 neurons are initialized

        // Check that each neuron has the correct number of weights
        for (Neuron neuron : layer.getNeurons()) {
            assertEquals(2, neuron.getWeights().length);
        }
    }

    @Test
    public void testForwardPropagation() {
        Layer layer = new Layer(2, 2); // 2 neurons, each with 2 inputs

        // Set known weights and biases for testing
        layer.getNeurons().get(0).setWeights(new double[]{0.5, 0.5});
        layer.getNeurons().get(0).setBias(0.5);
        layer.getNeurons().get(1).setWeights(new double[]{0.2, 0.8});
        layer.getNeurons().get(1).setBias(0.1);

        // Test forward propagation with known inputs
        var outputs = layer.forward(new double[]{1, 1});
        assertEquals(2, outputs.size()); // Ensure 2 outputs are produced (one for each neuron)

        // ... Assertions based on expected outputs for each neuron
        // For simplicity, you can manually calculate expected outputs based on the weights and biases set above
        // and compare them with the outputs produced by the layer.
    }

    // ... Additional tests for backpropagation and other functionalities once implemented
}
