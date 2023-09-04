package neuralnetwork.optimizers;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Neuron;
import neuralnetwork.functions.regularization.Regularized;
import neuralnetwork.functions.regularization.Regularizer;

import java.util.HashMap;
import java.util.Map;

public class MomentumOptimizer implements Optimizer, Regularized {
    private double momentumCoefficient;
    private Regularizer regularizer;
    private Map<Connection, Double> previousWeightUpdates = new HashMap<>();
    private Map<Neuron, Double> previousBiasUpdates = new HashMap<>();

    public MomentumOptimizer(double momentumCoefficient, Regularizer regularizer) {
        this.momentumCoefficient = momentumCoefficient;
        this.regularizer = regularizer;
    }

    @Override
    public void updateWeights(Neuron neuron, double learningRate) {
        for (Connection connection : neuron.getInputConnections()) {
            double weight = connection.getWeight();
            double gradient = neuron.getDelta() * connection.getFromNeuron().getOutput();
            double regularizationDerivative = regularizer.computeDerivative(weight);

            double previousUpdate = previousWeightUpdates.getOrDefault(connection, 0.0);
            double newUpdate = momentumCoefficient * previousUpdate + learningRate * (gradient + regularizationDerivative);

            connection.setWeight(weight - newUpdate);
            previousWeightUpdates.put(connection, newUpdate);
        }
    }

    @Override
    public void updateBias(Neuron neuron, double learningRate) {
        double biasGradient = neuron.getDelta();
        double previousUpdate = previousBiasUpdates.getOrDefault(neuron, 0.0);
        double newUpdate = momentumCoefficient * previousUpdate + learningRate * biasGradient;

        neuron.setBias(neuron.getBias() + newUpdate);
        previousBiasUpdates.put(neuron, newUpdate);
    }

    @Override
    public String getDescription() {
        return "Momentum Optimizer with coefficient = " + momentumCoefficient + " and " + regularizer.getDescription();
    }

	@Override
	public Regularizer getRegularizer() {
		return regularizer;
	}
}
