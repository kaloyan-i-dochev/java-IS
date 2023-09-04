package PuzzleSolvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class BlindPuzzleSolver extends PuzzleSolver {

    private Random random;

    public BlindPuzzleSolver(PuzzleState initialState) {
        super(initialState);
        this.random = new Random();
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();
        List<Direction> path = new ArrayList<>();
        PuzzleState currentState = new PuzzleState(this.initialState.getGrid());

        // Measure execution time and memory usage
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();

        while (!currentState.isResolved()) {
            Direction direction = Direction.values()[random.nextInt(Direction.values().length)];

            if (currentState.canMove(direction)) {
                currentState = currentState.transform(direction);
                path.add(direction);
            }
        }

        // Set the metrics and path in the PuzzleSolution
        long endTime = System.currentTimeMillis();
        long endMemoryUsage = getCurrentMemoryUsage();
        solution.setExecutionTime(endTime - startTime);
        solution.setMemoryUsage(endMemoryUsage - startMemoryUsage);
        solution.setPath(path);

        return solution;
    }
}

