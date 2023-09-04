package NBC;

import java.util.HashMap;
import java.util.Map;

import common.DataEntry;
import common.DataSet;
import common.InformationTheoryUtils;

/**
 * Implements the Naïve Bayes classifier for categorical data.
 */
public class NaiveBayes {

    /**
     * Stores the prior probabilities of each class label.
     */
    private Map<String, Double> classProbabilities;

    /**
     * Stores the conditional probabilities of each attribute given a class.
     */
    private Map<String, Map<String, Double>> conditionalProbabilities;

    /**
     * Constructs a NaiveBayes classifier and trains it using the provided dataset.
     *
     * @param trainingData The dataset used for training.
     */
    public NaiveBayes(DataSet trainingData) {
        this.classProbabilities = InformationTheoryUtils.calculateClassProbabilities(trainingData);
        this.conditionalProbabilities = InformationTheoryUtils.calculateConditionalProbabilities(trainingData);
    }

    /**
     * Predicts the class label for a given data entry using the Naïve Bayes algorithm.
     *
     * @param entry The data entry to be classified.
     * @return The predicted class label.
     */
    public String predict(DataEntry entry) {
        String predictedClass = null;
        double maxPosterior = Double.NEGATIVE_INFINITY;

        for (String className : classProbabilities.keySet()) {
            double posterior = Math.log(classProbabilities.get(className)); // Using log to prevent underflow

            for (int i = 0; i < entry.getAttributesValues().size(); i++) {
                String attributeKey = i + "_" + entry.getAttribute(i);
                if (conditionalProbabilities.get(className).containsKey(attributeKey)) {
                    posterior += Math.log(conditionalProbabilities.get(className).get(attributeKey));
                }
            }

            if (posterior > maxPosterior) {
                maxPosterior = posterior;
                predictedClass = className;
            }
        }

        return predictedClass;
    }

    /**
     * Retrieves the prior probabilities of class labels.
     *
     * @return A map with class labels as keys and their probabilities as values.
     */
    public Map<String, Double> getClassProbabilities() {
        return classProbabilities;
    }

    /**
     * Retrieves the conditional probabilities of attributes given a class.
     *
     * @return A nested map with class labels as outer keys, attribute indices and values as inner keys, 
     *         and their conditional probabilities as values.
     */
    public Map<String, Map<String, Double>> getConditionalProbabilities() {
        return conditionalProbabilities;
    }
}
