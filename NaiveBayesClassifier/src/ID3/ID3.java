package ID3;

import common.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the ID3 decision tree algorithm.
 */
public class ID3 {
    private Node root;
    private DataSet trainingDataSet;

    /**
     * Constructor for the ID3 algorithm.
     *
     * @param trainingDataSet The dataset to train on.
     */
    public ID3(DataSet trainingDataSet) {
        this.trainingDataSet = trainingDataSet;
    }

    /**
     * Trains the ID3 algorithm on the provided dataset.
     *
     * @param dataSet The dataset to train on.
     * @return The root node of the trained decision tree.
     */
    public Node train(DataSet dataSet) {
        // Get the list of all attributes from the dataset.
        List<String> attributes = dataSet.getAttributeNames();

        // Build the decision tree using the provided dataset and attributes.
        root = buildTree(dataSet, attributes);

        return root;
    }

    /**
     * Predicts the class label for a given data entry.
     *
     * @param entry The data entry to predict.
     * @return The predicted class label.
     */
    public String predict(DataEntry entry) {
        return predict(entry, root);
    }

    /**
     * Recursively traverses the decision tree to predict the class label for a given data entry.
     *
     * @param entry The data entry to predict.
     * @param currentNode The current node being evaluated.
     * @return The predicted class label.
     */
    private String predict(DataEntry entry, Node currentNode) {
        // If the current node is a LeafNode, return its class label.
        if (currentNode instanceof LeafNode) {
            return ((LeafNode) currentNode).getClassLabel();
        }

        // Otherwise, it's a DecisionNode. Determine the next node based on the attribute value of the entry.
        DecisionNode decisionNode = (DecisionNode) currentNode;
        String attributeValue = entry.getAttribute(decisionNode.getAttribute());
        Node nextNode = decisionNode.getChildren().get(attributeValue);

        // If there's no child node for the attribute value (e.g., missing or unseen value), 
        // return the most common class label in the dataset.
        // This is a fallback mechanism.
        if (nextNode == null) {
            return trainingDataSet.getMostFrequentLabel(); // Assuming you have a reference to the dataSet in the ID3 class.
        }

        // Recursively predict using the next node.
        return predict(entry, nextNode);
    }

    /**
     * Builds the decision tree recursively.
     *
     * @param dataSet The dataset to build the tree on.
     * @param attributes The list of available attributes.
     * @return The root node of the built subtree.
     */
    private Node buildTree(DataSet dataSet, List<String> attributes) {
        // If all entries have the same label, return a LeafNode with that label.
        String commonLabel = dataSet.getCommonLabel();
        if (commonLabel != null) {
            return new LeafNode(commonLabel);
        }

        // If no attributes left or all predictors are the same, return a LeafNode with the most frequent label.
        if (attributes.isEmpty() || allPredictorsSame(dataSet)) {
            return new LeafNode(dataSet.getMostFrequentLabel());
        }

        // Select the best attribute to split on.
        String bestAttribute = selectBestAttribute(dataSet);
        Map<String, Node> branches = new HashMap<>();

        // Remove the best attribute from the list of available attributes.
        attributes.remove(bestAttribute);

        // For each value of the best attribute, partition the dataset and build a subtree.
        for (String value : dataSet.getUniqueAttributeValues(bestAttribute)) {
            DataSet subset = dataSet.getSubset(bestAttribute, value);
            if (subset.isEmpty()) {
                branches.put(value, new LeafNode(dataSet.getMostFrequentLabel()));
            } else {
                branches.put(value, buildTree(subset, new ArrayList<>(attributes)));
            }
        }

        return new DecisionNode(bestAttribute, branches);
    }

    /**
     * Checks if all predictors are the same for all data entries in the dataset.
     *
     * @param dataSet The dataset to check.
     * @return true if all predictors are the same, false otherwise.
     */
    private boolean allPredictorsSame(DataSet dataSet) {
        if (dataSet.getEntries().isEmpty()) {
            return false;
        }

        DataEntry referenceEntry = dataSet.getEntries().get(0);
        for (DataEntry entry : dataSet.getEntries()) {
            if (!entry.getAttributesValues().equals(referenceEntry.getAttributesValues())) {
                return false;
            }
        }

        return true;
    }

    
    /**
     * Selects the best attribute to split on from the dataset.
     * The best attribute is the one that results in the highest information gain when the dataset is partitioned based on it.
     *
     * @param dataSet The dataset from which to select the best attribute.
     * @return The name of the best attribute.
     */
    public static String selectBestAttribute(DataSet dataSet) {
        String bestAttribute = null;
        double maxInformationGain = Double.NEGATIVE_INFINITY;

        for (String attribute : dataSet.getAttributeNames()) {
            double currentInformationGain = InformationTheoryUtils.calculateInformationGain(dataSet, attribute);
            if (currentInformationGain > maxInformationGain) {
                maxInformationGain = currentInformationGain;
                bestAttribute = attribute;
            }
        }

        return bestAttribute;
    }


    // Additional methods and helper functions can be added as needed.
}
