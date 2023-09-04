package kMeans.test;

import kMeans.Cluster;
import kMeans.KMeans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import common.*;

public class KMeansTest {

    private List<Point> dataPoints;

    @BeforeEach
    public void setUp() {
        // Sample data points for testing
        dataPoints = Arrays.asList(
            new Point(new double[]{1, 1}),
            new Point(new double[]{2, 2}),
            new Point(new double[]{3, 3}),
            new Point(new double[]{8, 8}),
            new Point(new double[]{9, 9}),
            new Point(new double[]{10, 10})
        );
    }

    @Test
    public void testInitializationOfCentroids() {
        KMeans kMeans = new KMeans(2, dataPoints);
        kMeans.run();
        List<Cluster> clusters = kMeans.getClusters();

        Assertions.assertEquals(2, clusters.size(), "Expected number of clusters to be 2.");
        for (Cluster cluster : clusters) {
            Assertions.assertNotNull(cluster.getCentroid(), "Centroid should not be null.");
        }
    }

    @Test
    public void testAssignmentOfPointsToClusters() {
        KMeans kMeans = new KMeans(2, dataPoints);
        kMeans.run();
        List<Cluster> clusters = kMeans.getClusters();

        int totalPoints = 0;
        for (Cluster cluster : clusters) {
            totalPoints += cluster.getPoints().size();
        }

        Assertions.assertEquals(dataPoints.size(), totalPoints, "All data points should be assigned to clusters.");
    }

    @Test
    public void testOverallClusteringProcess() {
        KMeans kMeans = new KMeans(2, dataPoints);
        kMeans.run();
        List<Cluster> clusters = kMeans.getClusters();

        // Basic check to ensure points are clustered
        for (Cluster cluster : clusters) {
            Assertions.assertTrue(cluster.getPoints().size() > 0, "Each cluster should have at least one point.");
        }
    }
}
