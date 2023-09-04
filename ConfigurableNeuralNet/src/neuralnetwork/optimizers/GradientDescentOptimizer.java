package neuralnetwork.optimizers;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Neuron;
import neuralnetwork.functions.regularization.Regularized;
import neuralnetwork.functions.regularization.Regularizer;

public class GradientDescentOptimizer implements Optimizer, Regularized {
    private Regularizer regularizer;

    public GradientDescentOptimizer(Regularizer regularizer) {
        this.regularizer = regularizer;
    }

    @Override
    public void updateWeights(Neuron neuron, double learningRate) {
        for (Connection connection : neuron.getInputConnections()) {
            double weight = connection.getWeight();
            double gradient = neuron.getDelta() * connection.getFromNeuron().getOutput();
            double regularizationDerivative = regularizer.computeDerivative(weight);
            double newWeight = weight - learningRate * (gradient + regularizationDerivative);
            connection.setWeight(newWeight);
        }
    }

    @Override
    public void updateBias(Neuron neuron, double learningRate) {
        double biasGradient = neuron.getDelta();
        neuron.setBias(neuron.getBias() + learningRate * biasGradient);
    }

    @Override
    public String getDescription() {
        return "Gradient Descent Optimizer with " + regularizer.getDescription();
    }
    
	@Override
	public Regularizer getRegularizer() {
		return regularizer;
	}
}
