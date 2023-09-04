package Tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;
import PuzzleSolvers.PatternPuzzleSolver;
import PuzzleSolvers.Patterns.PatternDatabase;

public class PatternPuzzleSolverTest {
	@DisplayName("PatternPuzzleSolver Tests")
	@ParameterizedTest
	@MethodSource("puzzleSolverArguments")
	public void testPatternPuzzleSolver(PuzzleState initialState, PatternDatabase patternDatabase, List<Direction> expectedSolutionPath) {
	    long startMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	    long startTime = System.currentTimeMillis();

	    PatternPuzzleSolver solver = new PatternPuzzleSolver(initialState, patternDatabase);
	    PuzzleSolution solution = solver.solve();

	    long endTime = System.currentTimeMillis();
	    long endMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	    long databaseMemoryUsage = patternDatabase.getDatabaseSize() * (Integer.SIZE / 8);
	    long totalMemoryUsage = endMemoryUsage - startMemoryUsage;
	    long algorithmMemoryUsage = totalMemoryUsage - databaseMemoryUsage;

	    // Test solution path
	    Assertions.assertEquals(expectedSolutionPath, solution.getPath(),
	            "Incorrect solution path for input: " + initialState);

	    // Log time and memory usage
	    System.out.println("Test for:\n" + initialState.toString());
	    System.out.println("Total execution time: " + (endTime - startTime) + "ms");
	    System.out.println("Database memory usage: " + databaseMemoryUsage + "kb");
	    System.out.println("Algorithm memory usage: " + algorithmMemoryUsage + "kb");
	    System.out.println("Total memory usage: " + totalMemoryUsage + "kb");
	    System.out.println("");
	}

	private static Stream<Arguments> puzzleSolverArguments() {
	    // Setup pattern databases
	    PatternDatabase patternDatabase3x3 = new PatternDatabase(3, 3);
	    patternDatabase3x3.generateDatabase();
	    
//	    PatternDatabase patternDatabase4x4 = new PatternDatabase(4, 4);
//		Only use 4x4 if you have 16!×4 bytes (76.12 terabytes) of memory to spare
	    //	    patternDatabase4x4.generateDatabase();
	    // TODO: Generate databases if necessary

	    return Stream.of(
	        Arguments.of(new PuzzleState(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}), patternDatabase3x3, new ArrayList<Direction>())
//	        Arguments.of(new PuzzleState(new int[][]{{5, 1, 2, 3}, {9, 6, 7, 4}, {13, 10, 11, 8}, {14, 15, 12, 0}}), patternDatabase4x4, Arrays.asList(Direction.LEFT, Direction.LEFT, Direction.LEFT, Direction.UP, Direction.UP, Direction.UP, Direction.RIGHT, Direction.RIGHT, Direction.RIGHT, Direction.DOWN, Direction.DOWN, Direction.DOWN))
	        // TODO: Add more test cases
	    );
	}


}
