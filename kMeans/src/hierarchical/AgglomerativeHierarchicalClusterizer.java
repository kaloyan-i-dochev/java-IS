package hierarchical;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import common.*;

public class AgglomerativeHierarchicalClusterizer {
	private List<ClusterNode> clusters;
	private double[][] distanceMatrix;

	public AgglomerativeHierarchicalClusterizer(List<Point> dataPoints) {
		// Initialize each data point as its own cluster
		clusters = new ArrayList<>();
		for (Point point : dataPoints) {
			clusters.add(new ClusterNode(point));
		}
		// Initialize the distance matrix
		distanceMatrix = new double[dataPoints.size()][dataPoints.size()];
		for (int i = 0; i < dataPoints.size(); i++) {
			for (int j = 0; j < dataPoints.size(); j++) {
				distanceMatrix[i][j] = dataPoints.get(i).distanceTo(dataPoints.get(j));
			}
		}
	}

	public void run() {
		while (clusters.size() > 1) {
			System.out.println(clusters.size());
			// Find the two closest clusters
			int[] closestPair = findClosestClusters();
			ClusterNode cluster1 = clusters.get(closestPair[0]);
			ClusterNode cluster2 = clusters.get(closestPair[1]);

			// Merge the two closest clusters
			double mergeDistance = distanceMatrix[closestPair[0]][closestPair[1]];
			ClusterNode mergedCluster = new ClusterNode(cluster1, cluster2, mergeDistance);
			clusters.add(mergedCluster);
			clusters.remove(cluster1);
			clusters.remove(cluster2);

			// Update the distance matrix
			updateDistanceMatrix(mergedCluster, closestPair);
		}
	}

	public int[] findClosestClusters() {
		int index1 = -1;
		int index2 = -1;
		double minDistance = Double.MAX_VALUE;

		for (int i = 0; i < distanceMatrix.length; i++) {
			for (int j = i + 1; j < distanceMatrix[i].length; j++) {
				if (distanceMatrix[i][j] < minDistance) {
					minDistance = distanceMatrix[i][j];
					index1 = i;
					index2 = j;
				}
			}
		}

		return new int[] { index1, index2 };
	}

	private void updateDistanceMatrix(ClusterNode mergedCluster, int[] mergedIndices) {
		int newSize = distanceMatrix.length - 1; // We're merging two clusters into one
		double[][] newMatrix = new double[newSize][newSize];

		// Copy over the old distances, skipping the merged clusters
		int rowOffset = 0;
		for (int i = 0; i < distanceMatrix.length; i++) {
			if (i == mergedIndices[0] || i == mergedIndices[1]) {
				rowOffset++;
				continue;
			}
			int colOffset = 0;
			for (int j = 0; j < distanceMatrix[i].length; j++) {
				if (j == mergedIndices[0] || j == mergedIndices[1]) {
					colOffset++;
					continue;
				}
				newMatrix[i - rowOffset][j - colOffset] = distanceMatrix[i][j];
			}
		}

		// Calculate distances from the new merged cluster to all other clusters
		for (int i = 0; i < newSize - 1; i++) {
			double distance = calculateAverageLinkageDistance(mergedCluster, clusters.get(i));
			newMatrix[newSize - 1][i] = distance;
			newMatrix[i][newSize - 1] = distance; // Symmetric matrix
		}

		distanceMatrix = newMatrix;
	}

	private double calculateAverageLinkageDistance(ClusterNode cluster1, ClusterNode cluster2) {
		List<Point> points1 = cluster1.getAllPoints();
		List<Point> points2 = cluster2.getAllPoints();

		double totalDistance = 0.0;
		int numPairs = 0;

		for (Point point1 : points1) {
			for (Point point2 : points2) {
				totalDistance += point1.distanceTo(point2);
				numPairs++;
			}
		}

		return totalDistance / numPairs;
	}

    public List<ClusterNode> getTopNClusters(int n) {
        List<ClusterNode> resultClusters = new ArrayList<>();
        Queue<ClusterNode> queue = new LinkedList<>();

        // Start with the root of the dendrogram
        queue.add(getResult());

        while (!queue.isEmpty() && resultClusters.size() < n) {
            ClusterNode current = queue.poll();

            // If the current node is a leaf or we've reached the desired number of clusters
            if (current.isLeaf() || resultClusters.size() + queue.size() + 1 == n) {
                resultClusters.add(current);
            } else {
                // Otherwise, add its children to the queue for further exploration
                if (current.getLeftChild() != null) {
                    queue.add(current.getLeftChild());
                }
                if (current.getRightChild() != null) {
                    queue.add(current.getRightChild());
                }
            }
        }

        return resultClusters;
    }

	public ClusterNode getResult() {
		if (clusters != null && clusters.size() == 1)
			return clusters.get(0);
		else
			return null;
	}
}
