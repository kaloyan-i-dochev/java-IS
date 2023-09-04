package main.test;

import main.Network;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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
	public void testTrainingImprovement(String operationName, double[][] trainingInputs, double[][] trainingOutputs, int[] layerConfig,
	        int sessions, int epochs) {
	    Network network = new Network(layerConfig);
	    Map<String, Double> lastPredictions = new HashMap<>(); // To store the last prediction for each input set
	    Map<String, Double> initialPredictions = new HashMap<>(); // To store the initial prediction for each input set
	    double initialAccuracy = 0.0;

	    System.out.println("Testing operation: " + operationName);
	    System.out.println("Network layout: " + Arrays.toString(layerConfig));
	    System.out.println("Training sessions: " + sessions + " | Epochs per session: " + epochs);
	    System.out.println("====================================");

	    for (int i = 0; i < sessions; i++) {
	        network.train(trainingInputs, trainingOutputs, epochs); // Train for 100 epochs

	        // Test the network's performance after training
	        int correctPredictions = 0;
	        if (LOG_SESSION_RESULTS || (LOG_LAST_SESSION_RESULTS_OVERRIDE && i == sessions - 1))
	            System.out.println("Results after training session " + (i + 1) + " at " + epochs + " epochs:");
	        for (int j = 0; j < trainingInputs.length; j++) {
	            List<Double> predictionList = network.predict(trainingInputs[j]);
	            double prediction = predictionList.get(0);
	            if (isPredictionCorrect(predictionList, trainingOutputs[j])) {
	                correctPredictions++;
	            }
	            String inputKey = Arrays.toString(trainingInputs[j]);
	            double lastPrediction = lastPredictions.getOrDefault(inputKey, prediction); // Default to current prediction if not present
	            double delta = prediction - lastPrediction;
	            lastPredictions.put(inputKey, prediction); // Update the last prediction

	            // Store the initial predictions
	            if (i == 0) {
	                initialPredictions.put(inputKey, prediction);
	                initialAccuracy = (double) correctPredictions / trainingInputs.length;
	            }

	            if (LOG_SESSION_RESULTS || (LOG_LAST_SESSION_RESULTS_OVERRIDE && i == sessions - 1))
	            	System.out.printf(
	            		    "Input: %s | Expected: % .1f | Predicted: % .5f | Last Predicted: % .5f | Delta: % .5f\n",
	            		    inputKey, trainingOutputs[j][0], prediction, lastPrediction, delta);

	        }

	        double accuracy = (double) correctPredictions / trainingInputs.length;
	        if (LOG_SESSION_RESULTS || (LOG_LAST_SESSION_RESULTS_OVERRIDE && i == sessions - 1)) {
	            System.out.println("Accuracy for session " + (i + 1) + ": " + accuracy);
	            System.out.println("====================================");
	            System.out.println();
	        }
	    }
	}

	// @formatter:off
	private static Stream<Object[]> trainingDataProvider() {		
		return Stream.of(
	            // Example data:
	            new Object[][] {
	            	testParams("AND","Minimal",10,200),
//	            	testParams("AND","Simple",10,200),
//	            	testParams("AND","Deep",10,200),
//	            	testParams("AND","Wide",10,200),
//	            	testParams("AND","Mixed",10,200),
	            	testParams("OR","Minimal",10,200),
	            	testParams("NAND","Minimal",10,200),
	            	testParams("NOR","Minimal",10,200),
	            	testParams("AND","Simple",10,100),
//	            	testParams("OR","Simple",10,100),
	            	testParams("XOR","Simple",10,200_000),
	            	testParams("XNOR","Simple",10,200_000),
//	            	testParams("XOR","Minimal",10,5_000),
//	            	testParams("XOR","Simple",10,5_000),
//	            	testParams("XOR","Deep",10,5_000),
//	            	testParams("XOR","Wide",10,5_000),
//	            	testParams("XOR","Mixed",10,5_000),
//	            	testParams("OR","Minimal",10,200),
//	            	testParams("ENCODED","Conditional",10,200),
//	            	testParams("ENCODED","Conditional",10,100_000),
	            }
	        );
	}
	// @formatter:on
	

	// @formatter:off
	@SuppressWarnings("serial")
	private static Object[] testParams(String operationName, String networkLayoutName, int sessions, int epochs) {
		Map<String, int[]> networksLayouts = new HashMap<String, int[]>() {{
		    put("Minimal", new int[]{2, 1});
		    put("Simple", new int[]{2, 3, 1});
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
