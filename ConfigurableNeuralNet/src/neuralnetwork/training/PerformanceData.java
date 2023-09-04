package neuralnetwork.training;

import java.util.HashMap;
import java.util.Map;

public class PerformanceData {
    private double accuracy;
//    private static 
    private Map<String, Double> expectedOutputs = new HashMap<>();
    private Map<String, Double> predictions = new HashMap<>();
    private Map<String, Double> deltas = new HashMap<>();
    
    public void addExpectedOutput(String inputKey, double expectedOutput) {
        this.expectedOutputs.put(inputKey, expectedOutput);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String inputKey : predictions.keySet()) {
            double prediction = predictions.get(inputKey);
            double delta = deltas.get(inputKey);
            double expectedOutput = expectedOutputs.get(inputKey);
            double lastPrediction = prediction - delta; // Recalculate the last prediction using the delta

            sb.append(String.format("Input: %s | Expected: % .1f | Predicted: % .5f | Last Predicted: % .5f | Delta: % .5f\n",
                    inputKey, expectedOutput, prediction, lastPrediction, delta));
        }
        sb.append("Accuracy: ").append(accuracy).append("\n");
        sb.append("====================================\n");
        return sb.toString();
    }
    
    // Generic getters and setters

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public Map<String, Double> getPredictions() {
		return predictions;
	}

	public void setPredictions(Map<String, Double> predictions) {
		this.predictions = predictions;
	}

	public Map<String, Double> getDeltas() {
		return deltas;
	}

	public void setDeltas(Map<String, Double> deltas) {
		this.deltas = deltas;
	}

	public Map<String, Double> getExpectedOutputs() {
		return expectedOutputs;
	}

	public void setExpectedOutputs(Map<String, Double> expectedOutputs) {
		this.expectedOutputs = expectedOutputs;
	}

    // Getters, setters, and other methods as needed...
    
}
