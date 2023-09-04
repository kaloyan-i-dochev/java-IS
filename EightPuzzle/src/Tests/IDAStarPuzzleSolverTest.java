package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import Main.PuzzleSolution;
import Main.PuzzleState;
import PuzzleSolvers.IDAStarPuzzleSolver;
import Main.Direction;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class IDAStarPuzzleSolverTest {

    @ParameterizedTest
    @MethodSource("predefinedStateArguments")
    public void testSolveWithPredefinedStates(PuzzleState initialState, List<Direction> expectedPath) {
        IDAStarPuzzleSolver solver = new IDAStarPuzzleSolver(initialState);
        System.out.print("Initial state:\n" + initialState.toString());
        PuzzleSolution solution = solver.solve();
        System.out.println("Solution path: " + solution.getPath());
        System.out.println("Execution time: " + solution.getExecutionTime() + "ms");
        System.out.println("Memory usage: " + solution.getMemoryUsage() + "kb");
        System.out.println();
        Assertions.assertEquals(expectedPath, solution.getPath(), "Failed to find correct solution for initial state:\n" + initialState.toString());
    }

    private static Stream<Arguments> predefinedStateArguments() {
        // Provide your predefined initial states and expected solutions here
        return Stream.of(
        		Arguments.of(new PuzzleState(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}), Arrays.asList()),
        		Arguments.of(new PuzzleState(new int[][] { { 4, 2, 0 }, { 3, 1, 5 } }), Arrays.asList()),
        		Arguments.of(new PuzzleState(new int[][]{{5, 1, 2, 3}, {9, 6, 7, 4}, {13, 10, 11, 8}, {14, 15, 12, 0}}), Arrays.asList(Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.UP, Direction.UP, Direction.UP, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.DOWN, Direction.DOWN, Direction.DOWN)),
        		Arguments.of(new PuzzleState(new int[][]{ { 10, 3, 8, 9 }, { 7, 5, 2, 4 }, { 11, 0, 6, 1} }), Arrays.asList()),
        		Arguments.of(new PuzzleState(new int[][]{ { 3, 6, 0, 12 }, { 15, 1, 2, 9 }, { 13, 7, 10, 5}, { 11, 4, 14, 8} }), Arrays.asList())
            // Add more predefined states and expected solutions here...
        );
    }

    @ParameterizedTest
//	@ParameterizedTest(name = "[{index}] - Grid: {0}")
    @MethodSource("randomStateArguments")
    public void testSolveWithRandomStates(PuzzleState initialState) {
        IDAStarPuzzleSolver solver = new IDAStarPuzzleSolver(initialState);
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
        for (int rows = 2; rows <= 4; rows++) {
            for (int cols = 2; cols <= 4; cols++) {
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

