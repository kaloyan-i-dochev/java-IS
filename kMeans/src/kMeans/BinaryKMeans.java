package kMeans;

import java.util.ArrayList;
import java.util.List;

import common.*;

public class BinaryKMeans extends Clusterizer {

	private int k;
	private List<Point> allDataPoints;

	public BinaryKMeans(int k, List<Point> dataPoints) {
		super(dataPoints);
		this.k = k;
		// Initially, all data points belong to one cluster
		this.allDataPoints = new ArrayList<>(dataPoints);
		Cluster initialCluster = new Cluster(allDataPoints);
		clusters.add(initialCluster);
	}

	@Override
	public void run() {
		while (clusters.size() < k) {
			double maxReductionInError = Double.MIN_VALUE;
			Cluster bestClusterToSplit = null;
			List<Cluster> bestNewClusters = null;

			for (Cluster cluster : clusters) {
				// Split the current cluster into two using kMeans
				KMeans kMeans = new KMeans(2, cluster.getPoints());
				kMeans.run();
				List<Cluster> newClusters = kMeans.getClusters();

				// Calculate the reduction in error
				double originalError = cluster.calculateSSE();
				double newError = newClusters.stream().mapToDouble(Cluster::calculateSSE).sum();
				double reductionInError = originalError - newError;

				if (reductionInError > maxReductionInError) {
					maxReductionInError = reductionInError;
					bestClusterToSplit = cluster;
					bestNewClusters = newClusters;
				}
			}

			// Replace the best cluster to split with the two new clusters
			clusters.remove(bestClusterToSplit);
			clusters.addAll(bestNewClusters);
		}

		KMeans subClusterizer = new KMeans(clusters, allDataPoints);
		this.clusters = subClusterizer.getClusters();
	}

	public List<Cluster> getClusters() {
		return clusters;
	}
}
