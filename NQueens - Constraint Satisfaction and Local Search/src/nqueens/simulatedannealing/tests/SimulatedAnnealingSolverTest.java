package nqueens.simulatedannealing.tests;

import nqueens.simulatedannealing.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class SimulatedAnnealingSolverTest {

    @Test
    public void testInitialization() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        CostFunction costFunction = new NumberOfConflictingPairs();
        SimulatedAnnealingSolver solver = new SimulatedAnnealingSolver(board, costFunction);

        // There's no direct way to test that the solver was initialized correctly,
        // as all its fields are private. However, we can at least check that it doesn't throw an exception.
        assertNotNull(solver);
    }

    @Test
    public void testSolveWithInitialSolution() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        // Manually place queens in a known solution
        int[] queenPlacements = {0, 4, 7, 5, 2, 6, 1, 3};
        for (int i = 0; i < queenPlacements.length; i++) {
            board.placeQueen(i, queenPlacements[i]);
        }

        CostFunction costFunction = new NumberOfConflictingPairs();
        SimulatedAnnealingSolver solver = new SimulatedAnnealingSolver(board, costFunction);

        // The initial board is already a solution, so the cost should be 0
        assertEquals(0, costFunction.calculate(board));

        // After solving, the board should still be a solution
        solver.solve();
        assertNotNull(solver.getBoard());
        assertEquals(0, costFunction.calculate(solver.getBoard()));
    }
    
    @Test
    public void testSolveWithEmptyBoard() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        SimulatedAnnealingBoard initialBoard = new SimulatedAnnealingBoard(board);
        // Don't place any queens

        CostFunction costFunction = new NumberOfConflictingPairs();
        SimulatedAnnealingSolver solver = new SimulatedAnnealingSolver(board, costFunction);

        // The initial board has no conflicts, but it's not a solution
        assertEquals(0, costFunction.calculate(board));
        assertFalse(board.isResolved());

        // After solving, the board should be a solution
        solver.solve();
        SimulatedAnnealingBoard finalBoard = solver.getBoard();
        assertFalse(finalBoard.equals(initialBoard));
    }

    @Test
    public void testImprovement() {
        int runs = 1000;
        int improvements = 0;
        int changes = 0;
        int solved = 0;
        for (int i = 0; i < runs; i++) {
            SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
            // Initialize the board with some queens
            Random random = new Random();
            for (int row = 0; row < board.getSize(); row++) {
                board.placeQueen(row, random.nextInt(board.getSize()));
            }
            SimulatedAnnealingBoard initialBoard = new SimulatedAnnealingBoard(board); // Copy of initial state
            NumberOfConflictingPairs costFunction = new NumberOfConflictingPairs();
            SimulatedAnnealingSolver solver = new SimulatedAnnealingSolver(board, costFunction);
            int initialCost = costFunction.calculate(board);
            solver.solve();
            SimulatedAnnealingBoard finalBoard = solver.getBoard();
            int finalCost = costFunction.calculate(finalBoard);
            if (finalCost < initialCost) {
                improvements++;
            }
            if (finalCost == 0) {
            	solved++;
            }
            if (!finalBoard.equals(initialBoard)) {
                changes++;
            }
//            System.out.println("Run: " + i + " Result: " + (finalCost < initialCost) + " Initial: " + initialCost + " Final: " + finalCost);
        }
        double improvementRate = (double) improvements / runs;
        double solutionRate = (double) solved / runs;
        double changeRate = (double) changes / runs;
//        System.out.println("Change rate: " + changeRate);
//        System.out.println("Solution rate: " + solutionRate);
//        System.out.println("Improvement rate: " + improvementRate);
        assertTrue(improvementRate > 0.95, "The solver should improve the solution in more than 95% of the runs but was " + improvementRate + "%.");
    }

    @Test
    public void testDifferentParameters() {
        int size = 8;
        int runs = 10000;
        NumberOfConflictingPairs costFunction = new NumberOfConflictingPairs();

        // Define different sets of parameters
        double[][] parameters = {
        		{10.0, 0.003, 1, 0.9},
        		{50.0, 0.003, 1, 0.9},
                {100.0, 0.003, 1, 0.9},
                {200.0, 0.003, 1, 0.9},
                {400.0, 0.003, 1, 0.9},
        		{10.0, 0.003, 2, 0.9},
        		{50.0, 0.003, 2, 0.9},
                {100.0, 0.003, 2, 0.9},
                {200.0, 0.003, 2, 0.9},
                {400.0, 0.003, 2, 0.9},
        		{10.0, 0.003, 4, 0.9},
        		{50.0, 0.003, 4, 0.9},
                {100.0, 0.003, 4, 0.9},
                {200.0, 0.003, 4, 0.9},
                {400.0, 0.003, 4, 0.9},
        		{10.0, 0.003, 8, 0.9},
        		{50.0, 0.003, 8, 0.9},
                {100.0, 0.003, 8, 0.9},
                {200.0, 0.003, 8, 0.9},
                {400.0, 0.003, 8, 0.9},
        		{10.0, 0.003, 16, 0.9},
        		{50.0, 0.003, 16, 0.9},
                {100.0, 0.003, 16, 0.9},
                {200.0, 0.003, 16, 0.9},
                {400.0, 0.003, 16, 0.9},
        };

        for (double[] parameterSet : parameters) {
            double initialTemperature = parameterSet[0];
            double initialCoolingRate = parameterSet[1];
            int maxAttempts = (int) parameterSet[2];
            double coolingRateIncreaseFactor = parameterSet[3];

            int improvements = 0;
            int changes = 0;
            int solved = 0;
            long startTime = System.nanoTime();
            for (int i = 0; i < runs; i++) {
                SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(size);
                Random random = new Random();
                for (int row = 0; row < board.getSize(); row++) {
                    board.placeQueen(row, random.nextInt(board.getSize()));
                }
                SimulatedAnnealingBoard initialBoard = new SimulatedAnnealingBoard(board);
                SimulatedAnnealingSolver solver = new SimulatedAnnealingSolver(board, costFunction);
                int initialCost = costFunction.calculate(board);
                solver.solve(initialTemperature, initialCoolingRate, maxAttempts, coolingRateIncreaseFactor);
                SimulatedAnnealingBoard finalBoard = solver.getBoard();
                int finalCost = costFunction.calculate(finalBoard);
                if (finalCost < initialCost) {
                    improvements++;
                }
                if (finalCost == 0) {
                	solved++;
                }
                if (!finalBoard.equals(initialBoard)) {
                    changes++;
                }
            }
            long endTime = System.nanoTime();
            long duration = endTime - startTime; // Duration in nanoseconds
            double durationInSeconds = duration / 1_000_000_000.0; // Convert to seconds
            double improvementRate = (double) improvements / runs;
            double solutionRate = (double) solved / runs;
            double changeRate = (double) changes / runs;

            System.out.println("Parameters: " + initialTemperature + ", " + initialCoolingRate + ", " + maxAttempts + ", " + coolingRateIncreaseFactor);
            System.out.println("Improvement rate: " + improvementRate + " Solution rate: " + solutionRate + " Time: " + durationInSeconds + " seconds.");
            System.out.println();
//            assertTrue(improvementRate > 0.95, "The solver should improve the solution in more than 95% of the runs but was " + improvementRate + "%.");
        }
    }
}
