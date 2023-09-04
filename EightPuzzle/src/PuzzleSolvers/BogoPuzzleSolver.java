package PuzzleSolvers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class BogoPuzzleSolver extends PuzzleSolver {
    private final Random random = new Random();

    public BogoPuzzleSolver(PuzzleState initialState) {
        super(initialState);
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();
        // Getting maximum possible moves
        int maxMoves = factorial(this.initialState.getGrid().length * this.initialState.getGrid()[0].length);

        // Measure execution time and memory usage
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();

        while (true) {
            // Generating a random path
            List<Direction> randomPath = generateRandomPath(random.nextInt(maxMoves));
            PuzzleState copyState = new PuzzleState(this.initialState.getGrid());
            List<Direction> performedPath = new ArrayList<>();

            // Applying the moves
            for (Direction move : randomPath) {
                if (copyState.isResolved()) {
                    // Set the metrics and path in the PuzzleSolution
                    long endTime = System.currentTimeMillis();
                    long endMemoryUsage = getCurrentMemoryUsage();
                    solution.setExecutionTime(endTime - startTime);
                    solution.setMemoryUsage(endMemoryUsage - startMemoryUsage);
                    solution.setPath(performedPath);
                    return solution; // Return the PuzzleSolution up to the solved state
                }
                if (copyState.canMove(move)) {
                    copyState = copyState.transform(move);
                    performedPath.add(move); // Keep track of the performed moves
                } else {
                    break;
                }
            }

            // Checking if the state is resolved after the moves
            if (copyState.isResolved()) {
                // Set the metrics and path in the PuzzleSolution
                long endTime = System.currentTimeMillis();
                long endMemoryUsage = getCurrentMemoryUsage();
                solution.setExecutionTime(endTime - startTime);
                solution.setMemoryUsage(endMemoryUsage - startMemoryUsage);
                solution.setPath(performedPath);
                return solution;
            }
        }
    }


    private int factorial(int number) {
        int result = 1;
        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }
        return result;
    }

    private List<Direction> generateRandomPath(int length) {
        List<Direction> randomPath = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            randomPath.add(Direction.values()[random.nextInt(Direction.values().length)]);
        }
        return randomPath;
    }
}
