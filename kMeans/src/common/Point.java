package common;

import java.util.Arrays;

/**
 * Represents an n-dimensional data point.
 */
public class Point {

    /** Array of coordinates representing the point in n-dimensional space. */
    private double[] coordinates;

    /**
     * Constructs a Point with the given coordinates.
     *
     * @param coordinates Array of coordinates for the point.
     */
    public Point(double[] coordinates) {
        this.coordinates = coordinates.clone();
    }

    /**
     * Calculates the Euclidean distance between this point and another point.
     *
     * @param other The other point to which distance is to be calculated.
     * @return The Euclidean distance between the two points.
     */
    public double distanceTo(Point other) {
        double sum = 0.0;
        for (int i = 0; i < coordinates.length; i++) {
            sum += Math.pow(coordinates[i] - other.coordinates[i], 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Retrieves the coordinates of the point.
     *
     * @return Array of coordinates.
     */
    public double[] getCoordinates() {
        return coordinates.clone();
    }
    
    /**
     * Moves the point to the provided coordinates.
     *
     * @param newCoordinates The new coordinates to move the point to.
     * @throws IllegalArgumentException If the length of newCoordinates doesn't match the current coordinates.
     */
    public void moveTo(double[] newCoordinates) {
        if (newCoordinates.length != coordinates.length) {
            throw new IllegalArgumentException("The length of newCoordinates must match the current coordinates.");
        }
        this.coordinates = newCoordinates.clone();
    }

    /**
     * Returns a string representation of the Point.
     *
     * @return A string in the format "[x1, x2, ..., xn]".
     */
    @Override
    public String toString() {
        return Arrays.toString(coordinates);
    }

	public int getDimensions() {
		return coordinates.length;
	}
}
