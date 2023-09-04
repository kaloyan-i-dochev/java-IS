package PuzzleSolvers;

import java.util.ArrayList;
import java.util.List;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class HillClimbPuzzleSolver extends PuzzleSolver { // note may get stuck in a local optima

    public HillClimbPuzzleSolver(PuzzleState initialState) {
        super(initialState);
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();
        
        List<Direction> path = new ArrayList<>();
        PuzzleState currentState = new PuzzleState(initialState.getGrid());
        
        while (!currentState.isResolved()) {
            int currentScore = manhattanDistance(currentState);
            Direction bestMove = null;

            for (Direction direction : Direction.values()) {
                if (currentState.canMove(direction)) {
                    PuzzleState nextState = currentState.transform(direction);
                    int nextScore = manhattanDistance(nextState);

                    if (nextScore < currentScore) {
                        currentScore = nextScore;
                        bestMove = direction;
                    }
                }
            }

            if (bestMove == null) {
                break; // no improvement found, terminate
            } else {
                currentState = currentState.transform(bestMove);
                path.add(bestMove);
            }
        }

        long endTime = System.currentTimeMillis();
        long endMemoryUsage = getCurrentMemoryUsage();
        solution.setPath(path);
        solution.setExecutionTime(endTime - startTime);
        solution.setMemoryUsage(Math.max(endMemoryUsage - startMemoryUsage, 0));
        
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
}
