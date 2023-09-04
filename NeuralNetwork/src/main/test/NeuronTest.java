package main.test;

import main.Neuron;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NeuronTest {

    @Test
    public void testInitialization() {
        Neuron neuron = new Neuron(3); // 3 inputs
        assertNotNull(neuron); // Ensure neuron is created
        assertEquals(3, neuron.getWeights().length); // Ensure 3 weights are initialized
        // ... Additional assertions for bias and other properties
    }

    @Test
    public void testActivation() {
        Neuron neuron = new Neuron(2); // 2 inputs
        // Set known weights and bias for testing
        neuron.setWeights(new double[]{0.5, 0.5});
        neuron.setBias(0.5);
        
        double output = neuron.activate(new double[]{1, 1});
        // ... Assertions based on expected output
        // Ensure lastInputs is updated
        assertArrayEquals(new double[]{1, 1}, neuron.getLastInputs());
    }

    // ... Additional tests for backpropagation once implemented
}
