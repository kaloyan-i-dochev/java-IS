package PuzzleSolvers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;
import Main.PuzzleStateNode;

public class IDAStarPuzzleSolver extends PuzzleSolver {
    private int threshold;
    private PuzzleStateNode goalNode;
    private int maxThreshold = Integer.MAX_VALUE;
    private boolean memoryAssist;
    private HashMap<PuzzleState, Integer> transpositionTable = new HashMap<>();

    public IDAStarPuzzleSolver(PuzzleState initialState, boolean memoryAssist) {
        super(initialState);
        this.memoryAssist = memoryAssist;
        this.transpositionTable = new HashMap<>();
    }
    
    public IDAStarPuzzleSolver(PuzzleState initialState) {
    	// memory assist is off by default as it can reach high memory usage
    	// for it's speed optimization to not lead to out of memory errors a frontier and parent checks need to be implemented
    	this(initialState, false);
//    	this(initialState, true);
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();

        threshold = manhattanDistance(initialState);
        while (true) {
            int resultThreshold = search(new PuzzleStateNode(initialState, null, null, 0, threshold), 0);
            if (resultThreshold == -1) break;
            if (resultThreshold == maxThreshold) return solution;
            System.out.println("resultThreshold: " + resultThreshold);
            threshold = resultThreshold * 2;
            if (memoryAssist) {
                transpositionTable.clear();  // clear visited states for the next threshold
            }
        }

        long endTime = System.currentTimeMillis();
        long endMemoryUsage = getCurrentMemoryUsage();
        solution.setExecutionTime(endTime - startTime);
        solution.setMemoryUsage(endMemoryUsage - startMemoryUsage);
        solution.setPath(constructPath(goalNode));
        return solution;
    }

    private int search(PuzzleStateNode node, int costToState) {
        int heuristicEvaluation = costToState + manhattanDistance(node.getState());
        if (heuristicEvaluation > threshold) return heuristicEvaluation;
        if (node.getState().isResolved()) {
            goalNode = node;
            return -1;
        }
        
        if(memoryAssist) {
        	// Check transposition table
            if (transpositionTable.containsKey(node.getState()) && 
                transpositionTable.get(node.getState()) <= costToState) {
                return maxThreshold;
            }

            // Update transposition table
            transpositionTable.put(node.getState(), costToState);
        }

        int min = maxThreshold;
        for (Direction direction : Direction.values()) {
            if (node.getState().canMove(direction)) {
                PuzzleStateNode childNode = node.transform(direction);
                int resultThreshold = search(childNode, costToState + 1);
                if (resultThreshold == -1) return -1;
                if (resultThreshold < min) min = resultThreshold;
            }
        }
        return min;
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

    private List<Direction> constructPath(PuzzleStateNode node) {
        List<Direction> path = new LinkedList<>();
        while (node.getParent() != null) {
            path.add(0, node.getAction());
            node = node.getParent();
        }
        return path;
    }
}

