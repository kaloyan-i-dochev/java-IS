package kMeans;

import java.util.ArrayList;
import java.util.List;

import common.*;

public abstract class Clusterizer {

    protected List<Point> dataPoints;
    protected List<Cluster> clusters;

    public Clusterizer(List<Point> dataPoints) {
        this.dataPoints = dataPoints;
        this.clusters = new ArrayList<>();
    }

    /**
     * Executes the clustering algorithm.
     */
    public abstract void run();

    /**
     * Returns the clusters formed by the clustering algorithm.
     * @return List of clusters.
     */
    public List<Cluster> getClusters() {
        return clusters;
    }
}
