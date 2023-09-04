package neuralnetwork.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import neuralnetwork.core.*;
import neuralnetwork.functions.activation.ActivationFunction;
import neuralnetwork.functions.loss.LossFunction;
import neuralnetwork.functions.regularization.L1Regularizer;
import neuralnetwork.functions.regularization.L2Regularizer;
import neuralnetwork.functions.regularization.NoneRegularizer;
import neuralnetwork.functions.regularization.Regularizer;
import neuralnetwork.functions.termination.TargetAccuracyTermination;
import neuralnetwork.initializers.UniformRandomInitializer;
import neuralnetwork.metrics.MetricsLogger;
import neuralnetwork.metrics.TrainingMetrics;
import neuralnetwork.optimizers.GradientDescentOptimizer;
import neuralnetwork.optimizers.MomentumOptimizer;
import neuralnetwork.optimizers.Optimizer;
import neuralnetwork.training.ConstantLRScheduler;
import neuralnetwork.training.LearningRateScheduler;
import neuralnetwork.training.StepDecayScheduler;
import neuralnetwork.training.Trainer;
import neuralnetwork.utils.Pair;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class NetworkTrainingTest {

	private static final boolean LOG_LAST_SESSION_RESULTS_OVERRIDE = true;
	private static final boolean LOG_SESSION_RESULTS = true; // Set to false if you don't want to log results for each
																// session

	@ParameterizedTest
	@MethodSource("trainingDataProvider")
	public void testTrainingImprovement(String operationName, double[][] trainingInputs, double[][] trainingOutputs,
			int[] layerConfig, int sessions, int epochs, LearningRateScheduler scheduler,
			ActivationFunction activationFunction, LossFunction lossFunction, UniformRandomInitializer initializer,
			Optimizer optimizer, Regularizer regularizer) {

		// 1. Initialization
//		Optimizer optimizer = Optimizer.GRADIENT_DESCENT;
		Network network = Network.simpleFeedForward(layerConfig, lossFunction, activationFunction, initializer); // Example
																													// instantiation
		Trainer trainer = new Trainer(network, optimizer, lossFunction, scheduler, regularizer, new TargetAccuracyTermination(1.0));
		Map<String, Double> lastPredictions = new HashMap<>();
		Map<String, Double> initialPredictions = new HashMap<>();
		double initialAccuracy = 0.0;

		System.out.println("Testing operation: " + operationName);
		System.out.println("Training Scheduler: " + scheduler.getDescription());
		System.out.println("Network layout: " + Arrays.toString(layerConfig));
		System.out.println("Optimizer: " + optimizer.getDescription());
		System.out.println("Activation: " + activationFunction.getDescription());
		System.out.println("Initializer: " + initializer.getDescription());
		System.out.println("Loss Function: " + lossFunction.getDescription());
		System.out.println("Training sessions: " + sessions + " | Epochs per session: " + epochs);
		System.out.println("====================================");

		TrainingMetrics sessionResult = trainer.train(trainingInputs, trainingOutputs, epochs, sessions);
		MetricsLogger logger = new MetricsLogger();
//		logger.logWeights(sessionResult);
//		logger.logWeightsDetailed(sessionResult);
	}

	// @formatter:off
	private static Stream<Object[]> trainingDataProvider() {		
		return Stream.of(
	            // Example data:
	            new Object[][] {
//	            	testParams("AND", "Minimal", 10, 1000, 
//	            			   new StepDecayScheduler(0.05,0.05,100), 
//	            	           ActivationFunction.SIGMOID, 
//	            	           LossFunction.MEAN_SQUARED_ERROR, 
//	            	           new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05), 
//	            	           new GradientDescentOptimizer(new NoneRegularizer()),
//	            	           new NoneRegularizer()),
//	            	testParams("AND", "Minimal", 10, 1000, 
//	            			   new StepDecayScheduler(0.05,0.05,100), 
//	            	           ActivationFunction.SIGMOID, 
//	            	           LossFunction.MEAN_SQUARED_ERROR, 
//	            	           new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05), 
//	            	           new MomentumOptimizer(0.01,new NoneRegularizer()),
//	            	           new NoneRegularizer()),
//	            	testParams("AND", "Minimal", 10, 1000, 
//	            			   new StepDecayScheduler(0.05,0.05,100), 
//	            	           ActivationFunction.SIGMOID, 
//	            	           LossFunction.MEAN_SQUARED_ERROR, 
//	            	           new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05), 
//	            	           new GradientDescentOptimizer(new L1Regularizer(0.01)),
//	            	           new L1Regularizer(0.01)),
//	            	testParams("AND", "Minimal", 10, 1000, 
//	            			   new StepDecayScheduler(0.05,0.05,100), 
//	            	           ActivationFunction.SIGMOID, 
//	            	           LossFunction.MEAN_SQUARED_ERROR, 
//	            	           new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05), 
//	            	           new MomentumOptimizer(0.01,new NoneRegularizer()),
//	            	           new L1Regularizer(0.01)),
//	            	testParams("AND", "Minimal", 10, 1000, 
//	            			   new StepDecayScheduler(0.05,0.05,100), 
//	            	           ActivationFunction.SIGMOID, 
//	            	           LossFunction.MEAN_SQUARED_ERROR, 
//	            	           new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05), 
//	            	           new GradientDescentOptimizer(new L1Regularizer(0.01)),
//	            	           new L2Regularizer(0.01)),
//	            	testParams("XOR", "Simple", 10, 1000, 
//	            			   new StepDecayScheduler(0.05,0.05,100), 
//	            	           ActivationFunction.SIGMOID, 
//	            	           LossFunction.MEAN_SQUARED_ERROR, 
//	            	           new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05), 
//	            	           new MomentumOptimizer(0.01,new NoneRegularizer()),
//	            	           new L2Regularizer(0.01)),
	            	testParams("XOR", "Simple", 50, 20, 
	            			   new ConstantLRScheduler(0.01), 
	            	           ActivationFunction.SIGMOID, 
	            	           LossFunction.MEAN_SQUARED_ERROR, 
	            	           new UniformRandomInitializer(1.0, 0.1, 1.0, -0.05), 
	            	           new MomentumOptimizer(0.01,new L2Regularizer(0.001)),
	            	           new L2Regularizer(0.01)),
	            }
	        );
	}
	// @formatter:on

	// @formatter:off
	@SuppressWarnings("serial")
	private static Object[] testParams(String operationName, 
            						   String networkLayoutName, 
            						   int sessions, 
            						   int epochs,
	                                   LearningRateScheduler scheduler,
            						   ActivationFunction activationFunction,
            						   LossFunction lossFunction,
            						   UniformRandomInitializer initializer,
            						   Optimizer optimizer,
            						   Regularizer regularizer) {
		Map<String, int[]> networksLayouts = new HashMap<String, int[]>() {{
		    put("Minimal", new int[]{2, 1});
		    put("Simple", new int[]{2, 2, 1});
		    put("Deep", new int[]{2, 4, 4, 4, 1});
		    put("Wide", new int[]{2, 8, 8, 1});
		    put("Mixed", new int[]{2, 5, 7, 3, 1});
		    put("Conditional", new int[]{3, 6, 6, 6, 1});
		}};
		Map<String, Pair<double[][], double[][]>> tasks = new HashMap<String, Pair<double[][], double[][]>>() {{
		    put("ENCODED", new Pair<>(new double[][]{
		        {0, 0, 0},
		        {0, 0, 1},
		        {0, 1, 0},
		        {0, 1, 1},
		        {1, 0, 0},
		        {1, 0, 0},
		        {1, 1, 0},
		        {1, 1, 1},
		    }, new double[][]{
		        {0},
		        {1},
		        {1},
		        {1},
		        {0},
		        {0},
		        {0},
		        {1},
		    }));
			
			put("AND", new Pair<>(new double[][]{
		        {0, 0},
		        {0, 1},
		        {1, 0},
		        {1, 1}
		    }, new double[][]{
		        {0},
		        {0},
		        {0},
		        {1}
		    }));

		    put("OR", new Pair<>(new double[][]{
		        {0, 0},
		        {0, 1},
		        {1, 0},
		        {1, 1}
		    }, new double[][]{
		        {0},
		        {1},
		        {1},
		        {1}
		    }));

		    put("NAND", new Pair<>(new double[][]{
		        {0, 0},
		        {0, 1},
		        {1, 0},
		        {1, 1}
		    }, new double[][]{
		        {1},
		        {1},
		        {1},
		        {0}
		    }));

		    put("NOR", new Pair<>(new double[][]{
		        {0, 0},
		        {0, 1},
		        {1, 0},
		        {1, 1}
		    }, new double[][]{
		        {1},
		        {0},
		        {0},
		        {0}
		    }));

		    put("XOR", new Pair<>(new double[][]{
		        {0, 0},
		        {0, 1},
		        {1, 0},
		        {1, 1}
		    }, new double[][]{
		        {0},
		        {1},
		        {1},
		        {0}
		    }));

		    put("XNOR", new Pair<>(new double[][]{
		        {0, 0},
		        {0, 1},
		        {1, 0},
		        {1, 1}
		    }, new double[][]{
		        {1},
		        {0},
		        {0},
		        {1}
		    }));
		}};

		
	    return new Object[] {
	            operationName,
	            tasks.get(operationName).getFirst(),
	            tasks.get(operationName).getSecond(),
	            networksLayouts.get(networkLayoutName),
	            sessions,
	            epochs,
	            scheduler,
	            activationFunction,
	            lossFunction,
	            initializer,
	            optimizer,
	            regularizer
	    };
	}
	// @formatter:on

	private boolean isPredictionCorrect(List<Double> prediction, double[] expectedOutput) {
		// For this problem, we can use a threshold of 0.5 to determine the predicted
		// class
		return (prediction.get(0) < 0.5 && expectedOutput[0] == 0)
				|| (prediction.get(0) >= 0.5 && expectedOutput[0] == 1);
	}
}
