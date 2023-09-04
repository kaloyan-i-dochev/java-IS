package kMeans;

import java.util.ArrayList;
import java.util.List;

import common.*;

/**
 * Represents a cluster of points with an associated centroid.
 */
public class Cluster {

    /** The centroid of the cluster. */
    private Centroid centroid;

    /** List of points that belong to this cluster. */
    private List<Point> points;


    /**
     * Default constructor. Initializes the centroid to a point at the origin.
     */
    public Cluster() {
        this.centroid = new Centroid(new double[]{0, 0}); // Default to 2D point at the origin. Adjust as needed.
        this.points = new ArrayList<>();
    }
    
    /**
     * Constructs a Cluster with the given centroid.
     *
     * @param centroid The centroid of the cluster.
     */
    public Cluster(Centroid centroid) {
        this.centroid = centroid;
        this.points = new ArrayList<>();
    }

    public Cluster(List<Point> dataPoints) {
    	this.points = dataPoints;
        this.centroid = new Centroid(points.get(0).getCoordinates());
	}

	/**
     * Adds a point to the cluster.
     *
     * @param point The point to be added.
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     * Clears all points from the cluster.
     */
    public void clearPoints() {
        points.clear();
    }

    /**
     * Updates the position of the centroid based on the average of its points.
     */
    public void updateCentroid() {
        if (points.isEmpty()) {
            return;
        }
        
        centroid.moveToAverageOfPoints(points);

//        double[] sum = new double[centroid.getCoordinates().length];
//        for (Point point : points) {
//            double[] coords = point.getCoordinates();
//            for (int i = 0; i < coords.length; i++) {
//                sum[i] += coords[i];
//            }
//        }
//
//        for (int i = 0; i < sum.length; i++) {
//            sum[i] /= points.size();
//        }
//
//        centroid.moveTo(sum);
    }

    /**
     * Retrieves the centroid of the cluster.
     *
     * @return The centroid of the cluster.
     */
    public Centroid getCentroid() {
        return centroid;
    }

    /**
     * Retrieves the points of the cluster.
     *
     * @return List of points that belong to the cluster.
     */
    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }
    
    public void merge(Cluster other) {
        this.points.addAll(other.points);
    }

    /**
     * Calculates the Sum of Squared Errors (SSE) for this cluster.
     * @return The SSE value.
     */
    public double calculateSSE() {
        return points.stream()
                     .mapToDouble(point -> Math.pow(point.distanceTo(centroid), 2))
                     .sum();
    }

	public List<Point> getAllPoints() {
		// TODO Auto-generated method stub
		return getPoints();
	}
}
