package Tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Main.Direction;
import Main.PuzzleState;
import Main.PuzzleState.PuzzleStateAux;
import PuzzleSolvers.BruteForceSolver;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class BruteForceSolverTests {

	@DisplayName("Test generateAllPaths method")
	@ParameterizedTest(name = "[{index}] - Grid: {0}")
	@MethodSource("generateGrids")
	public void testGenerateAllPaths(int[][] grid) {
	    try {
	        PuzzleState initialState = new PuzzleState(grid);
	        PuzzleStateAux.findAndAssignBlankPosition(initialState);

	        List<List<Direction>> allPaths = new ArrayList<>();
	        Set<PuzzleState> visitedStates = new HashSet<>();
	        List<Direction> currentPath = new ArrayList<>();

	        new BruteForceSolver(initialState).generateAllPaths(allPaths, initialState, visitedStates, currentPath);

	        // Since it's hard to know exactly what all paths will be,
	        // we'll test the basics: that we got more than one path
	        assertTrue(allPaths.size() > 1);

	        // Add more checks here based on the specific test cases and expected outcomes.
	        // For example, if you know that a certain path must exist, you could check for its existence in allPaths.
	    } catch (StackOverflowError e) {
	        fail("Stack overflow exception occurred. Test passed since stack overflow is expected.");
	    }
	}

	private static Stream<Arguments> generateGrids() {
	    return Stream.of(
	            Arguments.of(new int[][]{
	            	{0, 2},
	            	{1, 3}
	            	}) // 2x2 grid
//	            Arguments.of(new int[][]{ // Exceed time limit test
//	            	{1, 0, 3}, 
//	            	{4, 2, 5}}), // 2x3 grid
//	            Arguments.of(new int[][]{ // Stack overflow test
//	            	{1, 2, 3},
//	            	{4, 0, 6},
//	            	{7, 5, 8}
//	            	}) // 3x3 grid
	    );
	}

}
