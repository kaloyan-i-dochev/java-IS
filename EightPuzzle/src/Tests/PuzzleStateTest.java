package Tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import Main.Direction;
import Main.PuzzleState;
import Main.PuzzleState.PuzzleStateAux;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PuzzleStateTest {

	@DisplayName("Test PuzzleState Array Initialization")
	@ParameterizedTest
	@MethodSource("puzzleStateArguments")
	public void testPuzzleStateInitialization(int[][] initialState, int expectedRows, int expectedCols,
			int expectedBlankRow, int expectedBlankCol) {
		PuzzleState puzzleState = new PuzzleState(initialState);

		// Test array initialization
		Assertions.assertArrayEquals(initialState, puzzleState.getGrid(),
				"Array initialization failed for input: " + initialState);

		// Test blank tile position
		Assertions.assertEquals(expectedBlankRow, puzzleState.getBlankRow(),
				"Incorrect blank tile row for input: " + initialState);
		Assertions.assertEquals(expectedBlankCol, puzzleState.getBlankCol(),
				"Incorrect blank tile column for input: " + initialState);

		// Test puzzle dimensions
		Assertions.assertEquals(expectedRows, puzzleState.getGrid().length,
				"Incorrect number of rows for input: " + initialState);
		Assertions.assertEquals(expectedCols, puzzleState.getGrid()[0].length,
				"Incorrect number of columns for input: " + initialState);
	}

	private static Stream<Arguments> puzzleStateArguments() {
		return Stream.of(
				// 3x3 grid
				Arguments.of(new int[][] { { 1, 2, 3 }, { 4, 0, 5 }, { 6, 7, 8 } }, 3, 3, 1, 1),
				Arguments.of(new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } }, 3, 3, 0, 0),
				// 3x4 grid
				Arguments.of(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 0, 7 }, { 8, 9, 10, 11 } }, 3, 4, 1, 2),
				Arguments.of(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 0 }, { 8, 9, 10, 11 } }, 3, 4, 1, 3),
				// 4x3 grid
				Arguments.of(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 }, { 9, 10, 11 } }, 4, 3, 2, 1),
				Arguments.of(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }, { 9, 10, 11 } }, 4, 3, 2, 2),
				// 4x4 grid
				Arguments.of(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 0, 14, 15 } }, 4, 4,
						3, 1),
				Arguments.of(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } }, 4, 4,
						3, 3),
				// 5x5 grid
				Arguments.of(new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 },
						{ 16, 0, 17, 18, 19 }, { 20, 21, 22, 23, 24 } }, 5, 5, 3, 1),
				Arguments.of(new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 },
						{ 16, 17, 18, 19, 0 }, { 20, 21, 22, 23, 24 } }, 5, 5, 3, 4),
				// 3x5 grid
				Arguments.of(new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 0, 12, 13, 14 } }, 3, 5, 2, 1),
				Arguments.of(new int[][] { { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 0 } }, 3, 5, 2, 4));
	}

	@DisplayName("Test PuzzleState isSolvable")
	@ParameterizedTest
	@MethodSource("testCases")
	public void testIsSolvable(PuzzleState puzzleState, boolean expectedSolvable) {
		boolean isSolvable = PuzzleStateAux.isSolvable(puzzleState);
		Assertions.assertEquals(expectedSolvable, isSolvable,
				"Incorrect solvability for puzzle state: " + puzzleState.getGrid());
	}

	private static Stream<Arguments> testCases() {
		return Stream.of(
				// 3x3 grid
				Arguments.of(new PuzzleState(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } }), true),
				Arguments.of(new PuzzleState(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 8, 7, 0 } }), false),
				// 3x4 grid
				Arguments.of(new PuzzleState(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 0 } }), true),
				Arguments.of(new PuzzleState(new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 11, 10, 0 } }), false),
				// 4x4 grid
				Arguments.of(
						new PuzzleState(
								new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } }),
						true),
				Arguments.of(
						new PuzzleState(
								new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 15, 14, 0 } }),
						false),
				Arguments.of(
						new PuzzleState(
								new int[][] { { 4, 2, 0 }, { 3, 1, 5 } }),
						false),
				Arguments.of(
						new PuzzleState(
								new int[][] { { 10, 3, 8, 9 }, { 7, 5, 2, 4 }, { 11, 0, 6, 1} }),
						true)
		// Add more test cases here...
		);
	}

	@DisplayName("Test PuzzleState parityAdjustment")
	@ParameterizedTest
	@MethodSource("provideTestCases")
	public void testPerformParityAdjustment(PuzzleState puzzleState, boolean initialSolvable,
			boolean adjustedSolvable) {
		// Assert initial solvability
		Assertions.assertEquals(initialSolvable, PuzzleStateAux.isSolvable(puzzleState),
				"Incorrect initial solvability for puzzle state: " + puzzleState.getGrid());

		// Perform parity adjustment
		PuzzleStateAux.performParityAdjustment(puzzleState);

		// Assert adjusted solvability
		Assertions.assertEquals(adjustedSolvable, PuzzleStateAux.isSolvable(puzzleState),
				"Incorrect adjusted solvability for puzzle state:\n" + puzzleState.toString());
	}

	private static Stream<Arguments> provideTestCases() {
		return Stream.of(
				// 3x3 Grid - Solvable, Adjusted Solvable
				Arguments.of(new PuzzleState(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 0, 7, 8 } }), true, true),
				Arguments.of(new PuzzleState(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 0, 8, 7 } }), false, true),
				// 3x4 Grid - Unsolvable, Adjusted Solvable
				Arguments.of(new PuzzleState(new int[][] { { 8, 0, 2, 1 }, { 5, 3, 10, 9 }, { 7, 6, 11, 4 }, }), false,
						true),
				// 4x4 Grid - Solvable, Adjusted Solvable
				Arguments.of(
						new PuzzleState(
								new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 0, 13, 14, 15 } }),
						true, true),
				Arguments.of(
						new PuzzleState(
								new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 0, 14, 15 } }),
						true, true));
	}

	@DisplayName("Test PuzzleState Shuffled Initialization")
	@ParameterizedTest(name = "Puzzle Size: {0}x{1}, Empty Tile: ({2},{3})")
	@MethodSource("puzzleStateInitializationData")
	public void testPuzzleStateInitialization(int rows, int cols, int blankRow, int blankCol) {
		PuzzleState puzzleState = new PuzzleState(rows, cols, blankRow, blankCol);

		// Test puzzle grid dimensions
		Assertions.assertEquals(rows, puzzleState.getGrid().length, "Incorrect number of rows.");
		Assertions.assertEquals(cols, puzzleState.getGrid()[0].length, "Incorrect number of columns.");

		// Test all numbers are present and no duplicates
		Map<Integer, Integer> numberCount = new HashMap<>();
		int expectedCount = rows * cols - 1; // Exclude the empty tile (0)

		for (int i = 0; i < puzzleState.getGrid().length; i++) {
			for (int j = 0; j < puzzleState.getGrid()[i].length; j++) {
				int number = puzzleState.getGrid()[i][j];
				numberCount.put(number, numberCount.getOrDefault(number, 0) + 1);
			}
		}

		for (int i = 1; i <= expectedCount; i++) {
			Assertions.assertTrue(numberCount.containsKey(i), "Number " + i + " is missing.");
			Assertions.assertEquals(1, numberCount.get(i), "Number " + i + " has duplicates.");
		}

		// Verify puzzle solvability
		Assertions.assertTrue(PuzzleStateAux.isSolvable(puzzleState),
				"The puzzle state is not solvable:\n" + puzzleState.toString());
	}

	private static Stream<Arguments> puzzleStateInitializationData() {
		return Stream.of(Arguments.of(3, 3, 1, 1), // 3x3, Middle
				Arguments.of(3, 3, 0, 1), // 3x3, Top edge
				Arguments.of(3, 3, 1, 0), // 3x3, Left edge
				Arguments.of(3, 3, 1, 2), // 3x3, Right edge
				Arguments.of(3, 3, 2, 1), // 3x3, Bottom edge
				Arguments.of(3, 3, 0, 0), // 3x3, Top-left corner
				Arguments.of(3, 3, 0, 2), // 3x3, Top-right corner
				Arguments.of(3, 3, 2, 0), // 3x3, Bottom-left corner
				Arguments.of(3, 3, 2, 2), // 3x3, Bottom-right corner

				Arguments.of(3, 4, 1, 2), // 3x4, Middle
				Arguments.of(3, 4, 0, 1), // 3x4, Top edge
				Arguments.of(3, 4, 1, 0), // 3x4, Left edge
				Arguments.of(3, 4, 1, 3), // 3x4, Right edge
				Arguments.of(3, 4, 2, 1), // 3x4, Bottom edge
				Arguments.of(3, 4, 0, 0), // 3x4, Top-left corner
				Arguments.of(3, 4, 0, 3), // 3x4, Top-right corner
				Arguments.of(3, 4, 2, 0), // 3x4, Bottom-left corner
				Arguments.of(3, 4, 2, 3), // 3x4, Bottom-right corner

				Arguments.of(4, 3, 2, 1), // 4x3, Middle
				Arguments.of(4, 3, 0, 1), // 4x3, Top edge
				Arguments.of(4, 3, 1, 0), // 4x3, Left edge
				Arguments.of(4, 3, 2, 2), // 4x3, Right edge
				Arguments.of(4, 3, 3, 1), // 4x3, Bottom edge
				Arguments.of(4, 3, 0, 0), // 4x3, Top-left corner
				Arguments.of(4, 3, 0, 2), // 4x3, Top-right corner
				Arguments.of(4, 3, 3, 0), // 4x3, Bottom-left corner
				Arguments.of(4, 3, 3, 2), // 4x3, Bottom-right corner

				Arguments.of(4, 4, 1, 2), // 4x4, Middle
				Arguments.of(4, 4, 0, 1), // 4x4, Top edge
				Arguments.of(4, 4, 1, 0), // 4x4, Left edge
				Arguments.of(4, 4, 2, 3), // 4x4, Right edge
				Arguments.of(4, 4, 3, 1), // 4x4, Bottom edge
				Arguments.of(4, 4, 0, 0), // 4x4, Top-left corner
				Arguments.of(4, 4, 0, 3), // 4x4, Top-right corner
				Arguments.of(4, 4, 3, 0), // 4x4, Bottom-left corner
				Arguments.of(4, 4, 3, 3), // 4x4, Bottom-right corner

				Arguments.of(5, 5, 2, 2), // 5x5, Middle
				Arguments.of(5, 5, 0, 1), // 5x5, Top edge
				Arguments.of(5, 5, 1, 0), // 5x5, Left edge
				Arguments.of(5, 5, 2, 4), // 5x5, Right edge
				Arguments.of(5, 5, 4, 1), // 5x5, Bottom edge
				Arguments.of(5, 5, 0, 0), // 5x5, Top-left corner
				Arguments.of(5, 5, 0, 4), // 5x5, Top-right corner
				Arguments.of(5, 5, 4, 0), // 5x5, Bottom-left corner
				Arguments.of(5, 5, 4, 4), // 5x5, Bottom-right corner

				Arguments.of(3, 5, 1, 2), // 3x5, Middle
				Arguments.of(3, 5, 0, 1), // 3x5, Top edge
				Arguments.of(3, 5, 1, 0), // 3x5, Left edge
				Arguments.of(3, 5, 1, 4), // 3x5, Right edge
				Arguments.of(3, 5, 2, 1), // 3x5, Bottom edge
				Arguments.of(3, 5, 0, 0), // 3x5, Top-left corner
				Arguments.of(3, 5, 0, 4), // 3x5, Top-right corner
				Arguments.of(3, 5, 2, 0), // 3x5, Bottom-left corner
				Arguments.of(3, 5, 2, 4) // 3x5, Bottom-right corner
		);
	}

	@DisplayName("Test canMove method")
	@ParameterizedTest(name = "[{index}] - Grid: {0}, Direction: {1}, Expected Result: {2}")
	@MethodSource("canMoveTestData")
	public void testCanMove(int[][] grid, Direction direction, boolean expectedResult) {
		PuzzleState puzzleState = new PuzzleState(grid);

		boolean actualResult = puzzleState.canMove(direction);

		assertEquals(expectedResult, actualResult,
				String.format("Incorrect result for grid \n%s, direction %s. Expected: %b", puzzleState.toString(),
						direction, expectedResult));
	}

	private static Stream<Arguments> canMoveTestData() {
		int[][][] grids = { { { 1, 2, 3 }, { 4, 0, 5 }, { 6, 7, 8 } }, { { 1, 2, 3 }, { 0, 4, 5 }, { 6, 7, 8 } },
				{ { 1, 0, 3 }, { 4, 2, 5 }, { 6, 7, 8 } }, { { 1, 2, 3 }, { 4, 5, 0 }, { 6, 7, 8 } },
				{ { 1, 2, 3 }, { 4, 7, 5 }, { 6, 0, 8 } }, };
		return Stream.of(
				// Test cases: grid, direction, expected result
				// Test grid 1
				Arguments.of(grids[0], Direction.UP, true), Arguments.of(grids[0], Direction.DOWN, true),
				Arguments.of(grids[0], Direction.LEFT, true), Arguments.of(grids[0], Direction.RIGHT, true),
				// Test grid 2
				Arguments.of(grids[1], Direction.UP, true), Arguments.of(grids[1], Direction.DOWN, true),
				Arguments.of(grids[1], Direction.LEFT, false), Arguments.of(grids[1], Direction.RIGHT, true),
				// Test grid 3
				Arguments.of(grids[2], Direction.UP, false), Arguments.of(grids[2], Direction.DOWN, true),
				Arguments.of(grids[2], Direction.LEFT, true), Arguments.of(grids[2], Direction.RIGHT, true),
				// Test grid 4
				Arguments.of(grids[3], Direction.UP, true), Arguments.of(grids[3], Direction.DOWN, true),
				Arguments.of(grids[3], Direction.LEFT, true), Arguments.of(grids[3], Direction.RIGHT, false),
				// Test grid 5
				Arguments.of(grids[4], Direction.UP, true), Arguments.of(grids[4], Direction.DOWN, false),
				Arguments.of(grids[4], Direction.LEFT, true), Arguments.of(grids[4], Direction.RIGHT, true)
		// Add more test cases...
		);
	}

	@DisplayName("Test transform method")
	@ParameterizedTest(name = "[{index}] - Initial Grid: {0}, Direction: {1}, Expected Grid: {2}")
	@MethodSource("transformTestData")
	public void testTransform(int[][] initialGrid, Direction direction, int[][] expectedGrid) {
		PuzzleState puzzleState = new PuzzleState(initialGrid);

		PuzzleState transformedState = puzzleState.transform(direction);
		int[][] actualGrid = transformedState.getGrid();

		assertArrayEquals(expectedGrid, actualGrid,
				String.format(
						"Incorrect transformation for initial grid:\n%sDirection: %s\nExpected grid:\n%sBut was:\n%s",
						PuzzleStateAux.toString(initialGrid), direction, PuzzleStateAux.toString(expectedGrid),
						PuzzleStateAux.toString(actualGrid)));
	}

	private static Stream<Arguments> transformTestData() {
        int[][][][] grids = {
        	{
        		{
        			{1, 2, 3},
        			{4, 0, 5},
        			{6, 7, 8}
        		},
        		{
        			{1, 0, 3},
        			{4, 2, 5},
        			{6, 7, 8}
        		},
        		{
        			{1, 2, 3},
        			{4, 7, 5},
        			{6, 0, 8}
        		},
        		{
        			{1, 2, 3},
        			{0, 4, 5},
        			{6, 7, 8}
        		},
        		{
        			{1, 2, 3},
        			{4, 5, 0},
        			{6, 7, 8}
        		}
        	},
        	{
        		{
        			{1, 2, 3},
        			{0, 4, 5},
        			{6, 7, 8}
        		},
        		{
        			{0, 2, 3},
        			{1, 4, 5},
        			{6, 7, 8}
        		},
        		{
        			{1, 2, 3},
        			{6, 4, 5},
        			{0, 7, 8}
        		},
        		{
        			{1, 2, 3},
        			{0, 4, 5},
        			{6, 7, 8}
        		},
        		{
        			{1, 2, 3},
        			{4, 0, 5},
        			{6, 7, 8}
        		},
        	}
        };

        
        // Add more initial grids and expected grids here...

        return Stream.of(
            // Test cases: initial grid, direction, expected grid
                Arguments.of(grids[0][0], Direction.UP, grids[0][1]),
                Arguments.of(grids[0][0], Direction.DOWN, grids[0][2]),
                Arguments.of(grids[0][0], Direction.LEFT, grids[0][3]),
                Arguments.of(grids[0][0], Direction.RIGHT, grids[0][4]),
                
                Arguments.of(grids[1][0], Direction.UP, grids[1][1]),
                Arguments.of(grids[1][0], Direction.DOWN, grids[1][2]),
                Arguments.of(grids[1][0], Direction.LEFT, grids[1][3]),
                Arguments.of(grids[1][0], Direction.RIGHT, grids[1][4])
            // Add more test cases...
        );
    }
}
