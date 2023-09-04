package neuralnetwork.metrics;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Layer;
import neuralnetwork.core.Network;
import neuralnetwork.core.Neuron;
import neuralnetwork.utils.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class TrainingMetrics {
    private LocalDateTime timestamp;

	private Map<Integer, Double> errors; // Error for each epoch
	// Outer map: epoch -> (Inner map: actual -> predicted)
	private Map<Integer, Map<List<Double>, List<Double>>> predictions = new HashMap<>();
	private Map<Integer, Network> networkStates; // Network state after each epoch
	private Map<Integer, Map<String, String>> parameters; // Parameters for each epoch, e.g., learning rate, momentum,
															// etc.

	public TrainingMetrics() {
        this.timestamp = LocalDateTime.now();
        
		this.errors = new HashMap<>();
		this.predictions = new HashMap<>();
		this.networkStates = new HashMap<>();
		this.parameters = new HashMap<>();
	}

	public void logError(int epoch, double error) {
		errors.put(epoch, error);
	}

    public void logPrediction(int epoch, List<Double> inputs, List<Double> predicted) {
        // Check if there's already a map for this epoch
        Map<List<Double>, List<Double>> epochPredictions = predictions.getOrDefault(epoch, new HashMap<>());
        
        // Add the actuals and predicted to the epoch's map
        epochPredictions.put(inputs, predicted);
        
        // Update the main map
        predictions.put(epoch, epochPredictions);
    }
    
    public Map<Integer, List<Double>> getWeights() {
        Map<Integer, List<Double>> weightsMap = new HashMap<>();

        for (Map.Entry<Integer, Network> entry : networkStates.entrySet()) {
            int epoch = entry.getKey();
            Network network = entry.getValue();
            
            List<Double> weights = new ArrayList<>();
            for (Layer layer : network.getLayers()) {
                for (Neuron neuron : layer.getNeurons()) {
                    for (Connection conn : neuron.getInputConnections()) {
                        weights.add(conn.getWeight());
                    }
                }
            }
            
            weightsMap.put(epoch, weights);
        }

        return weightsMap;
    }


	public void logNetworkState(int epoch, Network networkState) {
		networkStates.put(epoch, networkState);
	}

	public void logParameters(int epoch, Map<String, String> parameterSet) {
		parameters.put(epoch, parameterSet);
	}

	// Getters and possibly setters if needed
	public Map<Integer, Double> getErrors() {
		return errors;
	}

	public Map<Integer, Map<List<Double>, List<Double>>> getPredictions() {
		return predictions;
	}

	public Map<Integer, Network> getNetworkStates() {
		return networkStates;
	}

	public Map<Integer, Map<String, String>> getParameters() {
		return parameters;
	}
	
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
