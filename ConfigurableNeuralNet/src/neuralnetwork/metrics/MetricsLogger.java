package neuralnetwork.metrics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import neuralnetwork.core.Connection;
import neuralnetwork.core.Layer;
import neuralnetwork.core.Network;
import neuralnetwork.core.Neuron;

public class MetricsLogger {
    private static final String BASE_DIR = "./logs"; // Define your base directory
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH-mm-ss");

    
    private String directoryPath;

    public void logWeights(TrainingMetrics metrics) {
        String directoryPath = getDirectoryName(metrics);
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = directoryPath + "/weights.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Integer, List<Double>> entry : metrics.getWeights().entrySet()) {
                int epoch = entry.getKey();
                List<Double> weights = entry.getValue();
                writer.write("Epoch " + epoch + ": ");
                for (Double weight : weights) {
                    writer.write(weight + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void logWeightsDetailed(TrainingMetrics metrics) {
        // Create directory based on timestamp
        String directoryName = getDirectoryName(metrics);
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        System.out.println("BUMP");
        
        // Create the weights file
        File weightsFile = new File(directory, "weights_detailed.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(weightsFile))) {
            // Iterate through each epoch's network state
            for (Map.Entry<Integer, Network> entry : metrics.getNetworkStates().entrySet()) {
                int epoch = entry.getKey();
                Network network = entry.getValue();

                writer.write("Epoch: " + epoch);
                writer.newLine();

                // Iterate through layers
                for (int layerIndex = 0; layerIndex < network.getLayers().size(); layerIndex++) {
                    Layer layer = network.getLayers().get(layerIndex);
                    for (Neuron neuron : layer.getNeurons()) {
                        for (Connection connection : neuron.getInputConnections()) {
                            writer.write(String.format("Layer %d - From Neuron ID: %d, To Neuron ID: %d, Weight: %.4f",
                                    layerIndex, connection.getFromNeuron().id, connection.getToNeuron().id, connection.getWeight()));
                            writer.newLine();
                        }
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Similarly, methods for logging predictions and network states can be added.
    
    private String getDirectoryName(TrainingMetrics metrics) {
        return BASE_DIR + "/" + metrics.getTimestamp().format(formatter);
    }

    private void createDirectoryIfNotExists(String directoryName) {
        Path path = Paths.get(directoryName);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + directoryName, e);
            }
        }
    }

    private void appendToFile(String filePath, String content) {
        try {
            Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file: " + filePath, e);
        }
    }
}
