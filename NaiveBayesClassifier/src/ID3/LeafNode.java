package ID3;

/**
 * Represents a leaf node in the ID3 decision tree.
 * This is a terminal node that contains a class label.
 */
public class LeafNode extends Node {

    private String classLabel;

    /**
     * Constructor for the LeafNode.
     *
     * @param classLabel The class label predicted by this leaf node.
     */
    public LeafNode(String classLabel) {
        this.classLabel = classLabel;
    }

    /**
     * Returns the class label predicted by this leaf node.
     *
     * @return The class label.
     */
    public String getClassLabel() {
        return classLabel;
    }

    /**
     * Sets the class label predicted by this leaf node.
     *
     * @param classLabel The class label.
     */
    public void setClassLabel(String classLabel) {
        this.classLabel = classLabel;
    }
}
