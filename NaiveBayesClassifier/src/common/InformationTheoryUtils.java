package common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for calculations related to information theory.
 */
public class InformationTheoryUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private InformationTheoryUtils() {}

    /**
     * Calculates the probabilities of each class in the dataset.
     *
     * @param dataSet The dataset on which probabilities will be calculated.
     * @return A map with class labels as keys and their probabilities as values.
     */
    public static Map<String, Double> calculateClassProbabilities(DataSet dataSet) {
        Map<String, Double> classProbabilities = new HashMap<>();
        double totalEntries = dataSet.getEntries().size();

        for (DataEntry entry : dataSet.getEntries()) {
            classProbabilities.put(entry.getLabel(), classProbabilities.getOrDefault(entry.getLabel(), 0.0) + 1.0);
        }

        for (Map.Entry<String, Double> entry : classProbabilities.entrySet()) {
            classProbabilities.put(entry.getKey(), entry.getValue() / totalEntries);
        }

        return classProbabilities;
    }

    /**
     * Calculates the conditional probabilities of each attribute given a class.
     *
     * @param dataSet The dataset on which probabilities will be calculated.
     * @return A nested map with class labels as outer keys, attribute indices and values as inner keys, 
     *         and their conditional probabilities as values.
     */
    public static Map<String, Map<String, Double>> calculateConditionalProbabilities(DataSet dataSet) {
        Map<String, Map<String, Double>> conditionalProbabilities = new HashMap<>();
        Map<String, Double> classCounts = new HashMap<>();

        for (DataEntry entry : dataSet.getEntries()) {
            String label = entry.getLabel();
            classCounts.put(label, classCounts.getOrDefault(label, 0.0) + 1.0);

            for (int i = 0; i < entry.getAttributesValues().size(); i++) {
                String attributeKey = i + "_" + entry.getAttribute(i);
                Map<String, Double> innerMap = conditionalProbabilities.getOrDefault(label, new HashMap<>());
                innerMap.put(attributeKey, innerMap.getOrDefault(attributeKey, 0.0) + 1.0);
                conditionalProbabilities.put(label, innerMap);
            }
        }

        for (Map.Entry<String, Map<String, Double>> outerEntry : conditionalProbabilities.entrySet()) {
            for (Map.Entry<String, Double> innerEntry : outerEntry.getValue().entrySet()) {
                outerEntry.getValue().put(innerEntry.getKey(), innerEntry.getValue() / classCounts.get(outerEntry.getKey()));
            }
        }

        return conditionalProbabilities;
    }
    
    /**
     * Calculates the entropy of a dataset.
     * Entropy measures the impurity or disorder of the class labels in the dataset.
     *
     * @param dataSet The dataset for which entropy is to be calculated.
     * @return The entropy value.
     */
    public static double calculateEntropy(DataSet dataSet) {
        // Get the frequency of each class label in the dataset
        Map<String, Long> labelFrequencies = dataSet.getEntries().stream()
            .collect(Collectors.groupingBy(DataEntry::getLabel, Collectors.counting()));

        double totalEntries = dataSet.getEntries().size();
        double entropy = 0.0;

        // Calculate entropy
        for (Long frequency : labelFrequencies.values()) {
            double proportion = frequency / totalEntries;
            entropy -= proportion * (Math.log(proportion) / Math.log(2));
        }

        return entropy;
    }

    /**
     * Calculates the information gain for a given attribute.
     * Information gain measures the reduction in entropy achieved by partitioning 
     * the dataset based on the attribute.
     *
     * @param dataSet The dataset for which information gain is to be calculated.
     * @param attribute The attribute for which information gain is to be calculated.
     * @return The information gain value.
     */
    public static double calculateInformationGain(DataSet dataSet, String attribute) {
        double originalEntropy = calculateEntropy(dataSet);
        double weightedEntropySum = 0.0;

        // Group the dataset by the values of the attribute
        Map<String, List<DataEntry>> partitions = dataSet.getEntries().stream()
            .collect(Collectors.groupingBy(entry -> (String) entry.getAttribute(attribute)));

        for (List<DataEntry> partition : partitions.values()) {
            double weight = (double) partition.size() / dataSet.getEntries().size();
            DataSet partitionDataSet = new DataSet(partition);
            weightedEntropySum += weight * calculateEntropy(partitionDataSet);
        }

        return originalEntropy - weightedEntropySum;
    }
    
    
}
