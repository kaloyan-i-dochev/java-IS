package kMeans;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.*;
import common.DataReader;

public class ClusterVisualizer extends Application {

    // Define default colors and shapes for the scatter chart
    private static final Color[] DEFAULT_COLORS = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.ORANGE,
            Color.MAGENTA,
            Color.CYAN,
            Color.PINK,
            Color.YELLOW,
    };

    private static final String[] DEFAULT_SHAPES = {
            "circle", "square", "diamond", "triangle", "cross", "x", "arrow", "star"
    };

    // Maintain a mapping between clusters and their shape-color combinations
    private Map<Cluster, ShapeColorCombo> clusterShapeColorMap = new HashMap<>();

	
	private static BinaryKMeans clusterizer;
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
		numClustersSpinner = new Spinner<>(1, 100, 1); // min: 1, max: 100, initial value: 1

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
			numClustersSpinner.commitValue(); // Commit any pending edits
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

	}

	private void displayClusters(int n) {
	    scatterChart.getData().clear(); // Clear previous data

	    try {
	        List<Point> dataPoints = DataReader.readDataPointsFromFile("data/unbalance/unbalance.txt");
//	        List<Point> dataPoints = DataReader.readDataPointsFromFile("data/normal/normal.txt");
	        clusterizer = new BinaryKMeans(n, dataPoints);
	        clusterizer.run();
	        List<Cluster> clusters = clusterizer.getClusters();

	        int index = 0;
	        for (Cluster cluster : clusters) {
	            XYChart.Series<Number, Number> series = new XYChart.Series<>();
	            for (Point point : cluster.getAllPoints()) {
	                double[] coords = point.getCoordinates();
	                series.getData().add(new XYChart.Data<>(coords[0], coords[1]));
	            }
	            scatterChart.getData().add(series);

	            // Assign colors and shapes to the clusters
	            ShapeColorCombo combo = clusterShapeColorMap.get(cluster);
	            if (combo == null) {
	                combo = new ShapeColorCombo(DEFAULT_COLORS[index % DEFAULT_COLORS.length], DEFAULT_SHAPES[index % DEFAULT_SHAPES.length]);
	                clusterShapeColorMap.put(cluster, combo);
	            }

	            ObservableList<XYChart.Data<Number, Number>> data = series.getData();
	            for (XYChart.Data<Number, Number> dataPoint : data) {
	                Node symbol = dataPoint.getNode();
	                symbol.setStyle("-fx-background-color: " + toRgbString(combo.color, 0.9, 0.9) + "; -fx-background-shape: " + combo.shape + ";");
	            }

	            index++;
	        }
	    } catch (IOException e) {
	        System.err.println("Error reading the file: " + e.getMessage());
	    }
	}


	public static void main(String[] args) {
		// Run the clustering algorithm once during initialization
		initializeClusterizer();
		launch(args);
	}

	/*
	 * @Override public void start(Stage stage) {
	 * stage.setTitle("kMeans Clustering Visualization");
	 * 
	 * // Define the x and y axes NumberAxis xAxis = new NumberAxis(); NumberAxis
	 * yAxis = new NumberAxis(); xAxis.setLabel("X Coordinate");
	 * yAxis.setLabel("Y Coordinate");
	 * 
	 * // Create the scatter chart ScatterChart<Number, Number> scatterChart = new
	 * ScatterChart<>(xAxis, yAxis); scatterChart.setTitle("Clusters");
	 * 
	 * try { // Read data points and run kMeans // List<Point> dataPoints =
	 * DataReader.readDataPointsFromFile("data/normal/normal.txt"); List<Point>
	 * dataPoints =
	 * DataReader.readDataPointsFromFile("data/unbalance/unbalance.txt"); // KMeans
	 * clusterizer = new KMeans(8, dataPoints); BinaryKMeans clusterizer = new
	 * BinaryKMeans(8, dataPoints); // AgglomerativeHierarchicalClusterizer
	 * clusterizer = new AgglomerativeHierarchicalClusterizer(dataPoints);
	 * clusterizer.run();
	 * 
	 * // Add data to the scatter chart // List<Cluster> clusters =
	 * clusterizer.getTopNClusters(2); List<Cluster> clusters =
	 * clusterizer.getClusters(); for (Cluster cluster : clusters) {
	 * XYChart.Series<Number, Number> series = new XYChart.Series<>(); for (Point
	 * point : cluster.getAllPoints()) { double[] coords = point.getCoordinates();
	 * series.getData().add(new XYChart.Data<>(coords[0], coords[1])); }
	 * scatterChart.getData().add(series); }
	 * 
	 * } catch (IOException e) { System.err.println("Error reading the file: " +
	 * e.getMessage()); }
	 * 
	 * // Display the scene Scene scene = new Scene(scatterChart, 1280, 960);
	 * stage.setScene(scene); stage.show(); }
	 * 
	 * public static void main(String[] args) { launch(args); }
	 */

	private static String toRgbString(Color color, double opacity, double darkness) {
	    Color adjustedColor = darker(color, darkness);
	    return String.format("rgba(%d, %d, %d, %.2f)", 
	                         adjustedColor.getRed(), 
	                         adjustedColor.getGreen(), 
	                         adjustedColor.getBlue(), 
	                         opacity);
	}

	
    public static Color darker(Color color, double factor) {
        return new Color(Math.max((int)(color.getRed()  *factor), 0),
                         Math.max((int)(color.getGreen()*factor), 0),
                         Math.max((int)(color.getBlue() *factor), 0),
                         color.getAlpha());
    }

    private static class ShapeColorCombo {
        private final Color color;
        private final String shape;

        public ShapeColorCombo(Color color, String shape) {
            this.color = color;
            this.shape = shape;
        }
    }
}
