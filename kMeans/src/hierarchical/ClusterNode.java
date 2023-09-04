package hierarchical;

import java.util.ArrayList;
import java.util.List;

import common.*;
import kMeans.Centroid;
import kMeans.Cluster;

public class ClusterNode extends Cluster{
    private Point dataPoint; // For leaf nodes
    private ClusterNode leftChild; // For internal nodes
    private ClusterNode rightChild; // For internal nodes
    private double distance; // Distance at which this node was formed by merging two clusters
    private Point centroid; // Centroid of the cluster
	private int size; // Number of data points in the cluster

    // Constructor for leaf nodes
    public ClusterNode(Point dataPoint) {
        this.setDataPoint(dataPoint);
        this.size = 1;
        this.centroid = dataPoint; // For a leaf node, the data point itself is the centroid
    }

    // Constructor for internal nodes
    public ClusterNode(ClusterNode leftChild, ClusterNode rightChild, double distance) {
        this.setLeftChild(leftChild);
        this.setRightChild(rightChild);
        this.setDistance(distance);
        this.size = leftChild.size + rightChild.size;
        // Compute the centroid of the merged cluster
        this.centroid = computeCentroid(leftChild, rightChild);
    }

    private Point computeCentroid(ClusterNode left, ClusterNode right) {
        // Compute the centroid based on the centroids and sizes of the merging clusters
        // This is a weighted average of the centroids
        double[] newCoordinates = new double[left.centroid.getDimensions()];
        for (int i = 0; i < newCoordinates.length; i++) {
            newCoordinates[i] = (left.centroid.getCoordinates()[i] * left.size + right.centroid.getCoordinates()[i] * right.size) / (left.size + right.size);
        }
        return new Point(newCoordinates);
    }
    
    public List<Point> getAllPoints() {
        List<Point> allPoints = new ArrayList<>();
        if (dataPoint != null) { // This is a leaf node
            allPoints.add(dataPoint);
        } else { // This is an internal node
            allPoints.addAll(leftChild.getAllPoints());
            allPoints.addAll(rightChild.getAllPoints());
        }
        return allPoints;
    }

	public boolean isLeaf() {
		return size == 1;
	}


	public Point getDataPoint() {
		return dataPoint;
	}

	public void setDataPoint(Point dataPoint) {
		this.dataPoint = dataPoint;
	}

	public ClusterNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(ClusterNode leftChild) {
		this.leftChild = leftChild;
	}

	public ClusterNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(ClusterNode rightChild) {
		this.rightChild = rightChild;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
    public Centroid getCentroid() {
		return (Centroid)centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
