package kMeans.test;

import kMeans.BinaryKMeans;
import kMeans.Cluster;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import common.*;

public class BinaryKMeansTest {

    @Test
    public void testInitialization() {
        Point p1 = new Point(new double[]{1.0, 1.0});
        Point p2 = new Point(new double[]{2.0, 2.0});
        List<Point> dataPoints = Arrays.asList(p1, p2);

        BinaryKMeans binaryKMeans = new BinaryKMeans(2, dataPoints);
        List<Cluster> clusters = binaryKMeans.getClusters();

        assertEquals(1, clusters.size());
        assertTrue(clusters.get(0).getPoints().containsAll(dataPoints));
    }

    @Test
    public void testRun_2() {
        Point p1 = new Point(new double[]{1.0, 1.0});
        Point p2 = new Point(new double[]{2.0, 2.0});
        Point p3 = new Point(new double[]{5.0, 5.0});
        Point p4 = new Point(new double[]{6.0, 6.0});
        List<Point> dataPoints = Arrays.asList(p1, p2, p3, p4);

        BinaryKMeans binaryKMeans = new BinaryKMeans(2, dataPoints);
        binaryKMeans.run();
        List<Cluster> clusters = binaryKMeans.getClusters();

        assertEquals(2, clusters.size());
    }

    @Test
    public void testRun_3() {
        Point p1 = new Point(new double[]{1.0, 1.0});
        Point p2 = new Point(new double[]{2.0, 2.0});
        Point p3 = new Point(new double[]{5.0, 5.0});
        Point p4 = new Point(new double[]{6.0, 6.0});
        List<Point> dataPoints = Arrays.asList(p1, p2, p3, p4);

        BinaryKMeans binaryKMeans = new BinaryKMeans(3, dataPoints);
        binaryKMeans.run();
        List<Cluster> clusters = binaryKMeans.getClusters();

        assertEquals(3, clusters.size());
    }

    @Test
    public void testErrorReduction() {
        Point p1 = new Point(new double[]{1.0, 1.0});
        Point p2 = new Point(new double[]{2.0, 2.0});
        Point p3 = new Point(new double[]{5.0, 5.0});
        Point p4 = new Point(new double[]{6.0, 6.0});
        List<Point> dataPoints = Arrays.asList(p1, p2, p3, p4);

        BinaryKMeans binaryKMeans = new BinaryKMeans(2, dataPoints);
        double initialError = binaryKMeans.getClusters().get(0).calculateSSE();
        binaryKMeans.run();
        double finalError = binaryKMeans.getClusters().stream().mapToDouble(Cluster::calculateSSE).sum();

        assertTrue(finalError < initialError);
    }
}
