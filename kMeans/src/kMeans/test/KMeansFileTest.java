package kMeans.test;

import kMeans.KMeans;
import kMeans.Cluster;
import kMeans.DataReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import common.*;

public class KMeansFileTest {

    @Test
    public void testKMeansWithFileData() {
        try {
        	int expectedPointCount = 100;
        	int expectedClusterCount = 4;
            // Read data points from the file
            List<Point> dataPoints = DataReader.readDataPointsFromFile("data/normal/normal.txt");

            // Ensure data points were read
            Assertions.assertTrue(!dataPoints.isEmpty(), "Data points should be read from the file.");
            Assertions.assertEquals(dataPoints.size(), expectedPointCount, "There should be " + expectedPointCount + "Data points should be ");

            // Run kMeans algorithm
            KMeans kMeans = new KMeans(expectedClusterCount, dataPoints); // Assuming 3 clusters for this example
            kMeans.run();

            // Retrieve the clusters
            List<Cluster> clusters = kMeans.getClusters();

            // Basic checks
            Assertions.assertEquals(expectedClusterCount, clusters.size(), "Expected number of clusters to be 3.");
            for (Cluster cluster : clusters) {
                Assertions.assertTrue(cluster.getPoints().size() > 0, "Each cluster should have at least one point.");
            }

        } catch (IOException e) {
            Assertions.fail("Error reading the file: " + e.getMessage());
        }
    }
}
