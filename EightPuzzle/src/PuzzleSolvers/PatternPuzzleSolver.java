package PuzzleSolvers;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;
import PuzzleSolvers.Patterns.PatternDatabase;

import java.util.*;

public class PatternPuzzleSolver extends PuzzleSolver {
    private PatternDatabase patternDatabase;

    public PatternPuzzleSolver(PuzzleState initialState, PatternDatabase patternDatabase) {
        super(initialState);
        this.patternDatabase = patternDatabase;
    }

    @Override
    public PuzzleSolution solve() {
        long startTime = System.currentTimeMillis();
        long startMemory = getCurrentMemoryUsage();

        // Priority queue for the frontier with a comparator that sorts states by their cost
        PriorityQueue<Node> frontier = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.cost + patternDatabase.queryDatabase(node.state))
        );

        // Set for the explored states
        Set<PuzzleState> explored = new HashSet<>();

        // Map to store the best path to a state
        Map<PuzzleState, Node> nodes = new HashMap<>();

        // Add the initial state to the frontier with a cost of 0
        Node initialNode = new Node(initialState, null, null, 0);
        frontier.add(initialNode);
        nodes.put(initialState, initialNode);

        while (!frontier.isEmpty()) {
            // Get the state in the frontier with the lowest cost
            Node currentNode = frontier.poll();
            PuzzleState currentState = currentNode.state;

            // If this state is the goal state, construct the solution
            if (currentState.equals(patternDatabase.getGoalState())) {
                PuzzleSolution solution = new PuzzleSolution();
                solution.setPath(constructPath(currentNode));
                solution.setExecutionTime(System.currentTimeMillis() - startTime);
                solution.setMemoryUsage(getCurrentMemoryUsage() - startMemory);
                return solution;
            }

            // Mark the current state as explored
            explored.add(currentState);

            // Generate all possible next states
            for (Direction direction : Direction.values()) {
                if (currentState.canMove(direction)) {
                    PuzzleState nextState = currentState.transform(direction);

                    // Only consider states that have not been explored yet or that have a lower cost than the current cost
                    Node nextNode = new Node(nextState, currentNode, direction, currentNode.cost + 1);
                    if (!explored.contains(nextState) && (!nodes.containsKey(nextState) || nextNode.cost < nodes.get(nextState).cost)) {
                        // Update the node for the next state
                        nodes.put(nextState, nextNode);

                        // Add the next state to the frontier
                        frontier.add(nextNode);
                    }
                }
            }
        }

        // If the frontier is empty and we haven't found a solution, return an indication that no solution exists
        return null;
    }

    private List<Direction> constructPath(Node node) {
        List<Direction> path = new ArrayList<>();
        while (node != null && node.action != null) {
            path.add(0, node.action);
            node = node.parent;
        }
        return path;
    }

    private static class Node {
        PuzzleState state;
        Node parent;
        Direction action;
        int cost;

        Node(PuzzleState state, Node parent, Direction action, int cost) {
            this.state = state;
            this.parent = parent;
            this.action = action;
            this.cost = cost;
        }
    }
}
