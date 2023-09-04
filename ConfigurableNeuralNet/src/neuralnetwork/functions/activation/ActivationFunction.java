package neuralnetwork.functions.activation;

import neuralnetwork.utils.Describable;

public interface ActivationFunction extends Describable {
    double activate(double x);
    double derivative(double x);

    default double initializeWeight() {
        return Math.random(); // Default random weight initialization
    }

    static ActivationFunction SIGMOID = new ActivationFunction() {
        @Override
        public double activate(double x) {
            return 1.0 / (1.0 + Math.exp(-x));
        }

        @Override
        public double derivative(double x) {
            double sigmoid = activate(x);
            return sigmoid * (1 - sigmoid);
        }
        
        @Override
		public String getDescription() {
        	return "SIGMOID activation";
        }
    };

    static ActivationFunction RELU = new ActivationFunction() {
        @Override
        public double activate(double x) {
            return Math.max(0, x);
        }

        @Override
        public double derivative(double x) {
            return x > 0 ? 1 : 0;
        }
        
        @Override
		public String getDescription() {
        	return "RELU activation";
        }
    };

    // ... other activation functions with their derivatives
}
