package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.*;

public class DataReader {

    /**
     * Reads data points from a file and returns a list of Point objects.
     *
     * @param filePath Path to the file containing the data points.
     * @return List of Point objects.
     * @throws IOException If there's an error reading the file.
     */
    public static List<Point> readDataPointsFromFile(String filePath) throws IOException {
        List<Point> dataPoints = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                double[] coordinates = new double[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    coordinates[i] = Double.parseDouble(parts[i]);
                }
                dataPoints.add(new Point(coordinates));
            }
        }

        return dataPoints;
    }
}
