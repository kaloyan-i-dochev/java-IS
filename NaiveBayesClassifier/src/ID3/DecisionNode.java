package ID3;

import java.util.Map;

/**
 * Represents a decision node in the ID3 decision tree.
 * This node contains a decision based on an attribute's value
 * and has child nodes for each possible value of the attribute.
 */
public class DecisionNode extends Node {

    private String attribute;
    private Map<String, Node> children;

    /**
     * Constructor for the DecisionNode.
     *
     * @param attribute The attribute on which the decision is based.
     * @param children A map of child nodes for each possible value of the attribute.
     */
    public DecisionNode(String attribute, Map<String, Node> children) {
        this.attribute = attribute;
        this.children = children;
    }

    /**
     * Returns the attribute on which the decision is based.
     *
     * @return The attribute name.
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Sets the attribute on which the decision is based.
     *
     * @param attribute The attribute name.
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Returns the child nodes for each possible value of the attribute.
     *
     * @return A map of child nodes.
     */
    public Map<String, Node> getChildren() {
        return children;
    }

    /**
     * Sets the child nodes for each possible value of the attribute.
     *
     * @param children A map of child nodes.
     */
    public void setChildren(Map<String, Node> children) {
        this.children = children;
    }

    /**
     * Adds a child node for a specific attribute value.
     *
     * @param attributeValue The value of the attribute.
     * @param child The child node corresponding to the attribute value.
     */
    public void addChild(String attributeValue, Node child) {
        children.put(attributeValue, child);
    }
}
