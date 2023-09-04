package PuzzleSolvers;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class IDDFSPuzzleSolver extends PuzzleSolver {

    public IDDFSPuzzleSolver(PuzzleState initialState) {
        super(initialState);
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();

        // Measure execution time and memory usage
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();

        int depth = 0;
        while (true) {
            Node result = DLS(depth);
            if (result != null) {
                List<Direction> path = constructPath(result);

                // Set the metrics and path in the PuzzleSolution
                long endTime = System.currentTimeMillis();
                long endMemoryUsage = getCurrentMemoryUsage();
                solution.setExecutionTime(endTime - startTime);
                solution.setMemoryUsage(endMemoryUsage - startMemoryUsage);
                solution.setPath(path);

                return solution;
            }
            depth++;
        }
    }

    private Node DLS(int limit) {
        Stack<Node> frontier = new Stack<>();
        Node root = new Node(initialState, null, null, 0);
        frontier.push(root);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.pop();

            if (currentNode.depth > limit) {
                continue;
            }

            if (currentNode.state.isResolved()) {
                return currentNode;
            }

            for (Direction direction : Direction.values()) {
                if (currentNode.state.canMove(direction)) {
                    PuzzleState nextState = currentNode.state.transform(direction);
                    Node childNode = new Node(nextState, currentNode, direction, currentNode.depth + 1);
                    frontier.push(childNode);
                }
            }
        }

        return null;
    }

    private List<Direction> constructPath(Node node) {
        List<Direction> path = new LinkedList<>();
        while (node.parent != null) {
            path.add(0, node.action);
            node = node.parent;
        }
        return path;
    }

    private static class Node {
        PuzzleState state;
        Node parent;
        Direction action;
        int depth;

        Node(PuzzleState state, Node parent, Direction action, int depth) {
            this.state = state;
            this.parent = parent;
            this.action = action;
            this.depth = depth;
        }
    }
}

