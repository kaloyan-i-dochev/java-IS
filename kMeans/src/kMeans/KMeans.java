package kMeans;

import java.util.List;
import java.util.Random;

import common.*;

/**
 * Implements the kMeans clustering algorithm.
 */
public class KMeans extends Clusterizer {
    /** Number of clusters. */
    private int numClusters;

    /** Maximum number of iterations for the algorithm. */
    private static final int MAX_ITERATIONS = 1000;

    /**
     * Constructs a KMeans instance with the given number of clusters and data points.
     *
     * @param numClusters Number of clusters.
     * @param dataPoints  List of data points to be clustered.
     */
    public KMeans(int numClusters, List<Point> dataPoints) {
        super(dataPoints);
        this.numClusters = numClusters;
        initializeClusters();
    }

    /**
     * Constructs a KMeans instance with the given clusters and data points.
     *
     * @param clusters    List of clusters.
     * @param dataPoints  List of data points to be clustered.
     */
    public KMeans(List<Cluster> clusters, List<Point> dataPoints) {
        super(dataPoints);
        this.clusters = clusters;
        this.numClusters = clusters.size();
    }

    /**
     * Initializes the clusters with random centroids from the data points.
     */
    private void initializeClusters() {
        Random random = new Random();
        for (int i = 0; i < numClusters; i++) {
            Point randomPoint = dataPoints.get(random.nextInt(dataPoints.size()));
            Cluster cluster = new Cluster(new Centroid(randomPoint.getCoordinates()));
            clusters.add(cluster);
        }
    }

    /**
     * Initializes the centroids of the clusters based on the points in each cluster.
     */
    private void initializeCentroids() {
        for (Cluster cluster : clusters) {
            cluster.updateCentroid();
        }
    }

    /**
     * Assigns each data point to the nearest cluster.
     */
    private void assignPointsToClusters() {
        for (Point point : dataPoints) {
            Cluster nearestCluster = null;
            double nearestDistance = Double.MAX_VALUE;
            for (Cluster cluster : clusters) {
                double distance = point.distanceTo(cluster.getCentroid());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestCluster = cluster;
                }
            }
            nearestCluster.addPoint(point);
        }
    }

    /**
     * Updates the clusters by recalculating their centroids.
     */
    private void updateClusters() {
        for (Cluster cluster : clusters) {
            cluster.updateCentroid();
        }
    }

    /**
     * Checks if the algorithm has converged. 
     * This can be further refined based on specific convergence criteria.
     *
     * @return true if converged, false otherwise.
     */
    private boolean hasConverged(int iteration) {
        return iteration >= MAX_ITERATIONS;
    }

    /**
     * Runs the kMeans clustering algorithm.
     */
    @Override
    public void run() {
        if (clusters.isEmpty()) {
            initializeClusters();
        }
        initializeCentroids();

        int iteration = 0;
        while (!hasConverged(iteration)) {
            for (Cluster cluster : clusters) {
                cluster.clearPoints();
            }

            assignPointsToClusters();
            updateClusters();

            iteration++;
        }
    }

    /**
     * Retrieves the clusters.
     *
     * @return List of clusters.
     */
    public List<Cluster> getClusters() {
        return clusters;
    }
}
