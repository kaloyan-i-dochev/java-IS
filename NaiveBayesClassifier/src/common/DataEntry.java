package common;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a single data entry with attributes and a label.
 */
public class DataEntry {

    private String label;
    private Map<String, String> attributes;

    /**
     * Constructs a new DataEntry with the given label and attributes.
     *
     * @param label      The class label of the data entry.
     * @param attributes The map of attribute names to their values.
     */
    public DataEntry(String label, Map<String, String> attributes) {
        this.label = label;
        this.attributes = attributes;
    }

    /**
     * Returns the class label of the data entry.
     *
     * @return The class label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the list of attribute values for the data entry.
     *
     * @return The list of attribute values.
     */
    public List<String> getAttributesValues() {
        return attributes.values().stream().collect(Collectors.toList());
    }

    /**
     * Returns the value of the specified attribute.
     *
     * @param attributeName The name of the attribute.
     * @return The value of the specified attribute.
     */
    public String getAttribute(String attributeName) {
        return attributes.get(attributeName);
    }

    /**
     * Returns the attribute value at the specified index.
     * This method is kept for backward compatibility.
     *
     * @param index The index of the attribute.
     * @return The attribute value at the specified index.
     */
    public String getAttribute(int index) {
        return (String) attributes.values().toArray()[index];
    }

    /**
     * Checks if the attribute with the given name is missing.
     *
     * @param attributeName The name of the attribute.
     * @return true if the attribute is missing, false otherwise.
     */
    public boolean isMissing(String attributeName) {
        return "?".equals(attributes.get(attributeName));
    }

    /**
     * Checks if the attribute at the specified index is missing.
     * This method is kept for backward compatibility.
     *
     * @param index The index of the attribute.
     * @return true if the attribute is missing, false otherwise.
     */
    public boolean isMissing(int index) {
        return "?".equals(getAttribute(index));
    }

    @Override
    public String toString() {
        return "DataEntry{" +
                "label='" + label + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
