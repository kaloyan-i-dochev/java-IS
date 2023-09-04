package kMeans;

import java.util.List;

import common.Point;

/**
 * Represents the centroid of a cluster, which is a special type of Point.
 */
public class Centroid extends Point {

    /**
     * Constructs a Centroid with the given coordinates.
     *
     * @param coordinates Array of coordinates for the centroid.
     */
    public Centroid(double[] coordinates) {
        super(coordinates);
    }

    /**
     * Adjusts the position of the centroid based on the average of a set of points.
     *
     * @param points List of points used to calculate the new position of the centroid.
     */
    public void moveToAverageOfPoints(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return;
        }

        double[] sum = new double[getCoordinates().length];
        for (Point point : points) {
            double[] coords = point.getCoordinates();
            for (int i = 0; i < coords.length; i++) {
                sum[i] += coords[i];
            }
        }

        for (int i = 0; i < sum.length; i++) {
            sum[i] /= points.size();
        }

        // Update the centroid's coordinates
        this.moveTo(sum);
    }
}
