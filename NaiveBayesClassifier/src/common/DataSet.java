package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a dataset containing multiple data entries.
 */
public class DataSet {

    private List<DataEntry> entries = new ArrayList<>();
    private List<String> attributeNames;

    /**
     * Constructs a new DataSet with a specified number of attributes.
     * The attribute names will be set as string values of their indexes.
     *
     * @param numAttributes The number of attributes.
     */
    public DataSet(int numAttributes) {
        this.attributeNames = IntStream.range(0, numAttributes)
                                       .mapToObj(String::valueOf)
                                       .collect(Collectors.toList());
    }

    /**
     * Constructs a new DataSet with a list of data entries.
     * The attribute names will be set based on the length of the attributes in the first data entry.
     *
     * @param entries The list of data entries.
     */
    public DataSet(List<DataEntry> entries) {
        if (entries == null || entries.isEmpty()) {
            throw new IllegalArgumentException("Entries list cannot be null or empty.");
        }
        this.entries = new ArrayList<>(entries);
        int numAttributes = entries.get(0).getAttributesValues().size();
        this.attributeNames = IntStream.range(0, numAttributes)
                                       .mapToObj(String::valueOf)
                                       .collect(Collectors.toList());
    }

    /**
     * Loads data from a file and populates the entries list.
     *
     * @param filePath The path to the data file.
     * @throws IOException If there's an error reading the file.
     */
    public void loadData(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Assuming the first line of the file contains attribute names
        String[] attributeNamesArray = reader.readLine().split(",");
        setAttributeNames(Arrays.asList(attributeNamesArray));

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String label = parts[0];
            Map<String, String> attributes = new HashMap<>();
            for (int i = 1; i < parts.length; i++) {
                attributes.put(attributeNamesArray[i - 1], parts[i]);
            }
            entries.add(new DataEntry(label, attributes));
        }
        reader.close();
    }
    
    /**
     * Returns a set of unique values for a given attribute from the dataset.
     *
     * @param attribute The attribute for which unique values are to be retrieved.
     * @return A set of unique values for the attribute.
     */
    public Set<String> getUniqueAttributeValues(String attribute) {
        return entries.stream()
                      .map(entry -> entry.getAttribute(attribute))
                      .collect(Collectors.toSet());
    }

    /**
     * Returns a subset of the dataset where the given attribute has the specified value.
     *
     * @param attribute The attribute based on which the subset is to be created.
     * @param value The value of the attribute for the subset.
     * @return A subset of the dataset.
     */
    public DataSet getSubset(String attribute, String value) {
        List<DataEntry> subsetEntries = entries.stream()
                                               .filter(entry -> value.equals(entry.getAttribute(attribute)))
                                               .collect(Collectors.toList());
        return new DataSet(subsetEntries);
    }

    /**
     * Splits the data into training and testing sets based on the given ratio.
     *
     * @param trainTestRatio The ratio of training data to total data. For example, 0.8 means 80% training data.
     * @return A list containing two lists: the training set and the testing set.
     */
    public List<List<DataEntry>> splitData(double trainTestRatio) {
        Collections.shuffle(entries);
        int trainSize = (int) (entries.size() * trainTestRatio);
        List<DataEntry> trainingData = entries.subList(0, trainSize);
        List<DataEntry> testingData = entries.subList(trainSize, entries.size());

        List<List<DataEntry>> result = new ArrayList<>();
        result.add(trainingData);
        result.add(testingData);
        return result;
    }
    
    /**
     * Sets the attributeNames list. This method can only be called once.
     * 
     * @param attributeNames The list of attribute names to set.
     */
    protected void setAttributeNames(List<String> attributeNames) {
        if (this.attributeNames != null) {
            throw new IllegalStateException("attributeNames has already been set.");
        }
        this.attributeNames = Collections.unmodifiableList(attributeNames);
    }

    /**
     * Returns an unmodifiable list of attribute names.
     * 
     * @return The list of attribute names.
     */
    public List<String> getAttributeNames() {
        return attributeNames;
    }

    /**
     * Returns the list of DataEntry objects in the dataset.
     *
     * @return The list of DataEntry objects.
     */
    public List<DataEntry> getEntries() {
        return entries;
    }

	public void addEntry(DataEntry dataEntry) {
		entries.add(dataEntry);		
	}

    /**
     * Returns a set of unique values for a given attribute.
     *
     * @param attributeIndex The index of the attribute in the DataEntry.
     * @return A set of unique values for the specified attribute.
     */
    public Set<String> getUniqueValues(int attributeIndex) {
        return entries.stream()
                      .map(entry -> entry.getAttribute(attributeIndex))
                      .collect(Collectors.toSet());
    }

    /**
     * Returns a new DataSet containing only the entries that have the specified value for the given attribute.
     *
     * @param attributeIndex The index of the attribute in the DataEntry.
     * @param value The value to filter entries by.
     * @return A new DataSet containing filtered entries.
     */
    public DataSet filterByAttribute(int attributeIndex, String value) {
        List<DataEntry> filteredEntries = entries.stream()
                                                 .filter(entry -> entry.getAttribute(attributeIndex).equals(value))
                                                 .collect(Collectors.toList());
        DataSet filteredDataSet = new DataSet(attributeNames.size());
        filteredDataSet.entries = filteredEntries;
        return filteredDataSet;
    }
    
    /**
     * Checks if all entries in the dataset have the same label. 
     * If they do, it returns that common label; otherwise, it returns null.
     *
     * @return The common label if all entries have the same label, or null otherwise.
     */
    public String getCommonLabel() {
        List<DataEntry> entries = getEntries();
        if (entries.isEmpty()) {
            return null;
        }

        String firstLabel = entries.get(0).getLabel();
        for (DataEntry entry : entries) {
            if (!entry.getLabel().equals(firstLabel)) {
                return null;
            }
        }
        return firstLabel;
    }

    /**
     * Returns the most common class label in the dataset.
     * Useful for determining the class label of impure leaf nodes.
     *
     * @return The most common class label in the dataset.
     */
    public String getMostFrequentLabel() {
        return entries.stream()
                      .map(DataEntry::getLabel)
                      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                      .entrySet().stream()
                      .max(Map.Entry.comparingByValue())
                      .map(Map.Entry::getKey)
                      .orElse(null);
    }

	public boolean isEmpty() {
		return entries.isEmpty();
	}

}
