package PuzzleSolvers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class DFSPuzzleSolver extends PuzzleSolver {

    public DFSPuzzleSolver(PuzzleState initialState) {
        super(initialState);
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();
        Stack<Node> frontier = new Stack<>();
        Map<PuzzleState, Node> explored = new HashMap<>();

        Node root = new Node(initialState, null, null);
        frontier.push(root);
        explored.put(root.state, root);

        // Measure execution time and memory usage
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.pop();

            if (currentNode.state.isResolved()) {
                List<Direction> path = constructPath(currentNode);

                // Set the metrics and path in the PuzzleSolution
                long endTime = System.currentTimeMillis();
                long endMemoryUsage = getCurrentMemoryUsage();
                solution.setExecutionTime(endTime - startTime);
                solution.setMemoryUsage(endMemoryUsage - startMemoryUsage);
                solution.setPath(path);

                return solution;
            }

            for (Direction direction : Direction.values()) {
                if (currentNode.state.canMove(direction)) {
                    PuzzleState nextState = currentNode.state.transform(direction);
                    Node childNode = new Node(nextState, currentNode, direction);

                    if (!explored.containsKey(nextState)) {
                        frontier.push(childNode);
                        explored.put(nextState, childNode);
                    }
                }
            }
        }

        // No solution found
        solution.setExecutionTime(System.currentTimeMillis() - startTime);
        solution.setMemoryUsage((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
        return solution;
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

        Node(PuzzleState state, Node parent, Direction action) {
            this.state = state;
            this.parent = parent;
            this.action = action;
        }
    }
}
