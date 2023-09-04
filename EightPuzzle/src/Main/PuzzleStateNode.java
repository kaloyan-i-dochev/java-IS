package Main;

public class PuzzleStateNode {
    private PuzzleState state;
    private PuzzleStateNode parent;
    private Direction action;
    private int cost;
    private int estimate;

    public PuzzleStateNode(PuzzleState state, PuzzleStateNode parent, Direction action, int cost, int estimate) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.cost = cost;
        this.estimate = estimate;
    }
    
    public PuzzleStateNode transform(Direction direction) {
        PuzzleState nextState = state.transform(direction);
        return new PuzzleStateNode(nextState, this, direction, cost + 1, estimate);
    }

    // Getters and Setters for all fields
    public PuzzleState getState() {
        return state;
    }

    public void setState(PuzzleState state) {
        this.state = state;
    }

    public PuzzleStateNode getParent() {
        return parent;
    }

    public void setParent(PuzzleStateNode parent) {
        this.parent = parent;
    }

    public Direction getAction() {
        return action;
    }

    public void setAction(Direction action) {
        this.action = action;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }
}
