package neuralnetwork.functions.loss;

import neuralnetwork.core.Network;
import neuralnetwork.utils.Describable;

public interface LossFunction extends Describable{
    
    // Computes the overall loss for a batch of predictions
    double compute(double[] actual, double[] predicted);

    // Computes the derivative of the loss with respect to a single neuron's output
    double computeDerivative(double actual, double predicted);

    static LossFunction MEAN_SQUARED_ERROR = new LossFunction() {
        @Override
        public double compute(double[] actual, double[] predicted) {
            double sum = 0.0;
            for (int i = 0; i < actual.length; i++) {
                sum += Math.pow(actual[i] - predicted[i], 2);
            }
            return sum / actual.length;
        }

        @Override
        public double computeDerivative(double actual, double predicted) {
            return predicted - actual; // Derivative of MSE with respect to predicted value
        }

		@Override
		public String getDescription() {
			return "Mean Squared Error Loss Function";
		}
    };

    static LossFunction CROSS_ENTROPY = new LossFunction() {
        @Override
        public double compute(double[] actual, double[] predicted) {
            double sum = 0.0;
            for (int i = 0; i < actual.length; i++) {
                sum += actual[i] * Math.log(predicted[i]);
            }
            return -sum;
        }

        @Override
        public double computeDerivative(double actual, double predicted) {
            // Derivative of cross-entropy with respect to predicted value
            // This is a simplified version for binary classification. 
            // For multi-class classification, the derivative would be different.
            return -(actual / predicted) + (1 - actual) / (1 - predicted);
        }

		@Override
		public String getDescription() {
			return "Cross Entropy Loss Function";
		}
    };

    // ... other loss functions
}
