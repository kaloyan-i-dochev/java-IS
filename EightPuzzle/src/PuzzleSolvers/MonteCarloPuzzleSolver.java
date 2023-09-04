package PuzzleSolvers;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class MonteCarloPuzzleSolver extends PuzzleSolver {
    private int numSamples;

    public MonteCarloPuzzleSolver(PuzzleState initialState, int numSamples) {
        super(initialState);
        this.numSamples = numSamples;
    }

    @Override
    public PuzzleSolution solve() {
        PuzzleSolution solution = new PuzzleSolution();
        long startTime = System.currentTimeMillis();
        long startMemoryUsage = getCurrentMemoryUsage();

        PuzzleState currentState = initialState;
        while (!currentState.isResolved()) {
            Direction bestMove = findBestMove(currentState);
            currentState = currentState.transform(bestMove);
            solution.getPath().add(bestMove);
        }

        long endTime = System.currentTimeMillis();
        long endMemoryUsage = getCurrentMemoryUsage();
        solution.setExecutionTime(endTime - startTime);
        solution.setMemoryUsage(endMemoryUsage - startMemoryUsage);
        return solution;
    }

    private Direction findBestMove(PuzzleState currentState) {
        Direction bestMove = null;
        int bestScore = Integer.MAX_VALUE;
        for (int i = 0; i < numSamples; i++) {
            Direction move = generateRandomMove();
            PuzzleState nextState = currentState.transform(move);
            int score = evaluateState(nextState);
            if (score < bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private Direction generateRandomMove() {
		return null;
        // This method should generate a random move.
    }

    private int evaluateState(PuzzleState state) {
		return numSamples;
        // This method should evaluate how good a state is.
    }
}

