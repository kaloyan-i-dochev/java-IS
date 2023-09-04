package PuzzleSolvers;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class AStarPuzzleSolver extends PuzzleSolver {
    public AStarPuzzleSolver(PuzzleState initialState) {
        super(initialState);
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();

        // Measure execution time and memory usage
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();

        // Priority queue with comparator to determine priority of nodes
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.pathCost + node.heuristicCost));
        Node root = new Node(initialState, null, null, 0, manhattanDistance(initialState));
        frontier.add(root);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();

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
                    int newPathCost = currentNode.pathCost + 1; // Assume all moves have a cost of 1
                    int heuristicCost = manhattanDistance(nextState);
                    Node childNode = new Node(nextState, currentNode, direction, newPathCost, heuristicCost);
                    frontier.add(childNode);
                }
            }
        }

        // No solution found
        return solution;
    }

    private int manhattanDistance(PuzzleState state) {
        int distance = 0;
        int[][] grid = state.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int value = grid[i][j];
                if (value != 0) {
                    int targetX = (value - 1) / grid.length;
                    int targetY = (value - 1) % grid[0].length;
                    int dx = i - targetX;
                    int dy = j - targetY;
                    distance += Math.abs(dx) + Math.abs(dy);
                }
            }
        }
        return distance;
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
        int pathCost;
        int heuristicCost;

        Node(PuzzleState state, Node parent, Direction action, int pathCost, int heuristicCost) {
            this.state = state;
            this.parent = parent;
            this.action = action;
            this.pathCost = pathCost;
            this.heuristicCost = heuristicCost;
        }
    }
}

