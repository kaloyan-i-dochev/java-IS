package hierarchical.test;

import common.*;
import hierarchical.AgglomerativeHierarchicalClusterizer;
import hierarchical.ClusterNode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AgglomerativeHierarchicalClusterizerTest {

    private AgglomerativeHierarchicalClusterizer clusterizer;

    @BeforeEach
    public void setUp() {
        // Initialize with some sample data points for each test
        Point p1 = new Point(new double[]{1.0, 1.0});
        Point p2 = new Point(new double[]{2.0, 2.0});
        Point p3 = new Point(new double[]{5.0, 5.0});
        Point p4 = new Point(new double[]{6.0, 6.0});
        List<Point> dataPoints = Arrays.asList(p1, p2, p3, p4);

        clusterizer = new AgglomerativeHierarchicalClusterizer(dataPoints);
    }

    @Test
    public void testRun() {
        clusterizer.run();
        ClusterNode result = clusterizer.getResult();

        // Assuming you want to end up with a single cluster
        assertNotNull(result);
        assertEquals(4, result.getAllPoints().size());
    }

    @Test
    public void testFindClosestClusters() {
        // This test might be a bit tricky since findClosestClusters is private.
        // You might need to change its access modifier or use reflection.
        // For the sake of this example, let's assume it's protected or package-private.

        int[] closestClusters = clusterizer.findClosestClusters();
        assertNotNull(closestClusters);
        assertEquals(2, closestClusters.length);
    }

    // ... You can add more tests for other methods and edge cases ...
}
