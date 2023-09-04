package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Implements k-fold cross-validation for evaluating the performance of classifiers.
 */
public class CrossValidator {

    private DataSet dataSet;
    private int k;
    private Function<DataEntry, String> classifierFunction;

    /**
     * Constructs a CrossValidator with the specified dataset, number of folds, and classifier function.
     *
     * @param dataSet           The dataset to be used for cross-validation.
     * @param k                 The number of folds.
     * @param classifierFunction The function responsible for classifying a given data entry.
     */
    public CrossValidator(DataSet dataSet, int k, Function<DataEntry, String> classifierFunction) {
        this.dataSet = dataSet;
        this.k = k;
        this.classifierFunction = classifierFunction;
    }

    public double validate() {
        List<DataEntry> entries = dataSet.getEntries();
        Collections.shuffle(entries);

        int foldSize = entries.size() / k;
        double totalAccuracy = 0.0;

        for (int i = 0; i < k; i++) {
            List<DataEntry> testSet = entries.subList(i * foldSize, (i + 1) * foldSize);
            List<DataEntry> trainingSet = new ArrayList<>(entries);
            trainingSet.removeAll(testSet);

            int correctPredictions = 0;
            for (DataEntry testEntry : testSet) {
                String predictedClass = classifierFunction.apply(testEntry);
                if (predictedClass.equals(testEntry.getLabel())) {
                    correctPredictions++;
                }
            }

            double accuracy = (double) correctPredictions / testSet.size();
            totalAccuracy += accuracy;
        }

        return totalAccuracy / k;
    }
}
