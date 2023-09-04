package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;
import Main.PuzzleState.PuzzleStateAux;
import PuzzleSolvers.BFSPuzzleSolver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BFSPuzzleSolverTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void testSolve(int[][] initialState, int[][] finalState, int maxPathSize) {
        PuzzleState startState = new PuzzleState(initialState);
        PuzzleState endState = new PuzzleState(finalState);

        BFSPuzzleSolver solver = new BFSPuzzleSolver(startState);
        PuzzleSolution solution = solver.solve();
        List<Direction> solutionPath = solution.getPath();
        

        // Apply solution to start state
        PuzzleState resultState = startState;
        for (Direction d : solutionPath) {
            assertTrue(resultState.canMove(d));
            resultState = resultState.transform(d);
        }

        // Check if the solution leads to the final state
        assertEquals(endState, resultState);

        // Check if the solution path size is within limit
        assertTrue(solutionPath.size() <= maxPathSize, "Actual path size exceeds expected maximum: " + solutionPath.size() + " > " + maxPathSize);

    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
            Arguments.of(new int[][] {{1, 2}, {3, 0}}, new int[][] {{1, 2}, {3, 0}}, 0), // already solved
            Arguments.of(new int[][] {{2, 0}, {1, 3}}, new int[][] {{1, 2}, {3, 0}}, 3), // simple 2x2 case
            Arguments.of(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}, new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}, 0), // already solved 3x3 case
            Arguments.of(new int[][] {{8, 1, 2}, {7, 0, 3}, {6, 5, 4}}, new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}, 31) // complex 3x3 case
            // add more test cases here
        );
    }
    
    @DisplayName("Test BFS Puzzle Solver with initial state")
    @ParameterizedTest
    @MethodSource("bfsPuzzleSolverArguments")
    public void testBFSPuzzleSolver(PuzzleState initialState, List<Direction> expectedSolution) {
        BFSPuzzleSolver solver = new BFSPuzzleSolver(initialState);
        PuzzleSolution solution = solver.solve();

        System.out.println("Initial state:\n" + initialState);
        System.out.println("Solution path: " + solution.getPath());
        System.out.println("Execution time: " + solution.getExecutionTime());
        System.out.println("Memory usage: " + solution.getMemoryUsage());
        
        // check if the state is solvable
        if (PuzzleStateAux.isSolvable(initialState)) {
            Assertions.assertIterableEquals(expectedSolution, solution.getPath(), 
                    "Solution path doesn't match the expected path for initial state: \n" + initialState);
        } else {
            Assertions.assertTrue(solution.getPath().isEmpty(), 
                    "The solution path should be empty for an unsolvable state: \n" + initialState);
        }
    }

    private static Stream<Arguments> bfsPuzzleSolverArguments() {
        return Stream.of(
                // 2x2 grid, already solved state
                Arguments.of(new PuzzleState(new int[][]{{1, 2}, {3, 0}}), Arrays.asList()),
                // 2x2 grid, one step to solve
                Arguments.of(new PuzzleState(new int[][]{{1, 0}, {3, 2}}), Arrays.asList(Direction.DOWN)),
                Arguments.of(new PuzzleState(new int[][] { { 4, 2, 0 }, { 3, 1, 5 } }), Arrays.asList()),
                // 3x3 grid, more complex case
                Arguments.of(new PuzzleState(new int[][]{{5, 1, 2}, {4, 0, 3}, {7, 8, 6}}), Arrays.asList()),
                // Add an unsolvable case
                Arguments.of(new PuzzleState(new int[][]{{1, 2}, {3, 1}}), Arrays.asList()),
                Arguments.of(new PuzzleState(new int[][]{ { 10, 3, 8, 9 }, { 7, 5, 2, 4 }, { 11, 0, 6, 1} }), Arrays.asList())
                // add more test cases here as needed
        );
    }
    
    @ParameterizedTest
    @MethodSource("randomStateArguments")
    public void testSolveWithRandomStates(PuzzleState initialState) {
        BFSPuzzleSolver solver = new BFSPuzzleSolver(initialState);
        System.out.print("Initial state:\n" + initialState.toString());
        PuzzleSolution solution = solver.solve();
        System.out.println("Solution path: " + solution.getPath());
        System.out.println("Execution time: " + solution.getExecutionTime() + "ms");
        System.out.println("Memory usage: " + solution.getMemoryUsage() + "kb");
        System.out.println();
        Assertions.assertNotNull(solution.getPath(), "Failed to find a solution for initial state:\n" + initialState.toString());
    }

    private static Stream<Arguments> randomStateArguments() {
        // Generate a large number of random states here
        Stream.Builder<Arguments> builder = Stream.builder();
        for (int rows = 2; rows <= 5; rows++) {
            for (int cols = 2; cols <= 5; cols++) {
                for (int j = 0; j < 10; j++) { // Generate 10 random states of each size
                    int blankRow = (int)(Math.random() * rows);
                    int blankCol = (int)(Math.random() * cols);
                    PuzzleState initialState = new PuzzleState(rows, cols, blankRow, blankCol);
                    builder.add(Arguments.of(initialState));
                }
            }
        }
        return builder.build();
    }
}

