package hierarchical;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import common.*;

import java.io.IOException;
import java.util.List;

public class AHCClusterVisualizer extends Application {

	private static AgglomerativeHierarchicalClusterizer clusterizer;
	private ScatterChart<Number, Number> scatterChart;
    private Spinner<Integer> numClustersSpinner;

	@Override
	public void start(Stage stage) {
		stage.setTitle("Hierarchical Clustering Visualization");

		// Define the x and y axes
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("X Coordinate");
		yAxis.setLabel("Y Coordinate");

		// Create the scatter chart
		scatterChart = new ScatterChart<>(xAxis, yAxis);
		scatterChart.setTitle("Clusters");

		// Create a Spinner for number input
        numClustersSpinner = new Spinner<>(1, 100, 1);  // min: 1, max: 100, initial value: 1

        // Make the Spinner editable
        numClustersSpinner.setEditable(true);

        // Add a text formatter to allow only numbers
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 1, change -> {
            if (!change.getControlNewText().matches("\\d*")) {
                return null;
            }
            return change;
        });
        numClustersSpinner.getEditor().setTextFormatter(formatter);

        // Commit the edit and update the chart when Enter key is pressed
        numClustersSpinner.getEditor().setOnAction(e -> {
            numClustersSpinner.commitValue();
            displayClusters(numClustersSpinner.getValue());
        });

        // Update the chart when the spinner value changes
        numClustersSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            displayClusters(newValue);
        });

        Button showClustersButton = new Button("Show Clusters");
        showClustersButton.setOnAction(e -> {
            numClustersSpinner.commitValue();  // Commit any pending edits
            displayClusters(numClustersSpinner.getValue());
        });

        // Arrange in a VBox layout
        VBox layout = new VBox(10, scatterChart, numClustersSpinner, showClustersButton);
        layout.setPadding(new Insets(10));

		// Display the scene
		Scene scene = new Scene(layout, 1280, 960);
		stage.setScene(scene);
		stage.show();
		
		// Initial display
//		displayClusters(numClustersSpinner.getValue());
		displayClusters(8);
	}

	private static void initializeClusterizer() {
		try {
//			List<Point> dataPoints = DataReader.readDataPointsFromFile("data/normal/normal.txt");
            List<Point> dataPoints = DataReader.readDataPointsFromFile("data/unbalance/unbalance.txt");
			clusterizer = new AgglomerativeHierarchicalClusterizer(dataPoints);
			clusterizer.run();
		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		}
	}

	private void displayClusters(int n) {
		scatterChart.getData().clear(); // Clear previous data

		List<ClusterNode> clusters = clusterizer.getTopNClusters(n);
		for (ClusterNode cluster : clusters) {
			XYChart.Series<Number, Number> series = new XYChart.Series<>();
			for (Point point : cluster.getAllPoints()) {
				double[] coords = point.getCoordinates();
				series.getData().add(new XYChart.Data<>(coords[0], coords[1]));
			}
			scatterChart.getData().add(series);
		}
	}

	public static void main(String[] args) {
		// Run the clustering algorithm once during initialization
		initializeClusterizer();
		launch(args);
	}
}
