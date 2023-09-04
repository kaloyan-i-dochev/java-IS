package nqueens.backtracking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class BacktrackingBoardTest {

	private static Stream<Arguments> provideBoardsAndPositions() {
		BacktrackingBoard board = new BacktrackingBoard(8);
		int[] queenPlacements = { 0, 4, 7, 5, 2, 6, 1, 3 };
		for (int i = 0; i < queenPlacements.length; i++) {
			board.placeQueen(i, queenPlacements[i]);
		}
		System.out.println("Placements: " + queenPlacements);
		System.out.println(board.toString());
		System.out.println(board.toString_endangered());
		System.out.println();

		return Stream.of(
	            // Unsafe positions
	            Arguments.of(board, 0, 0, true), Arguments.of(board, 1, 4, true), Arguments.of(board, 2, 7, true),
	            Arguments.of(board, 3, 5, true), Arguments.of(board, 4, 2, true), Arguments.of(board, 5, 6, true),
	            Arguments.of(board, 6, 1, true), Arguments.of(board, 7, 3, true),
	            // Safe positions
	            Arguments.of(board, 1, 2, false), Arguments.of(board, 2, 1, false), Arguments.of(board, 3, 0, false),
	            Arguments.of(board, 4, 6, false), Arguments.of(board, 5, 0, false), Arguments.of(board, 6, 7, false),
	            Arguments.of(board, 7, 1, false)
	    // Add more test cases as needed
	    );

	}

	@ParameterizedTest
	@MethodSource("provideBoardsAndPositions")
	public void testIsSafe(BacktrackingBoard board, int row, int column, boolean expected) {
//		System.out.println("Position (" + row + ", " + column + "). Expected: " + expected);
//		System.out.println(board.toString(row, column));
//		System.out.println(board.toString_endangered());
//		System.out.println();
		Assertions.assertEquals(expected, board.isSafe(row, column),
				"Unexpected result for position (" + row + ", " + column + "). Board:\n" + board.toTestString());
	}
	
	private static Stream<Arguments> provideBoards() {
	    BacktrackingBoard resolvedBoard = new BacktrackingBoard(8);
	    int[] queenPlacements = {0, 4, 7, 5, 2, 6, 1, 3};
	    for (int i = 0; i < queenPlacements.length; i++) {
	        resolvedBoard.placeQueen(i, queenPlacements[i]);
	    }

	    BacktrackingBoard unresolvedBoard = new BacktrackingBoard(8);
	    int[] queenPlacements2 = {0, 1, 2, 3, 4, 5, 6, 7};
	    for (int i = 0; i < queenPlacements2.length; i++) {
	        unresolvedBoard.placeQueen(i, queenPlacements2[i]);
	    }

	    return Stream.of(
	        Arguments.of(resolvedBoard, true),
	        Arguments.of(unresolvedBoard, false)
	    );
	}

	@ParameterizedTest
	@MethodSource("provideBoards")
	public void testIsResolved(BacktrackingBoard board, boolean expected) {
	    Assertions.assertEquals(expected, board.isResolved());
	}
	
	@Test
    public void testPlaceQueens_printEndangered() {
//        BacktrackingBoard board = new BacktrackingBoard(8);
//        System.out.println("Board with 0 queens:");
//        System.out.println(board.toString());
//        System.out.println("Endangered state of the board:");
//        System.out.println(board.toString_endangered());
//
//        // Place 1 queen
//        board.placeQueen(0, 0);
//        System.out.println("Board with 1 queen:");
//        System.out.println(board.toString());
//        System.out.println("Endangered state of the board:");
//        System.out.println(board.toString_endangered());
//
//        // Check 2nd queen
//        System.out.println("\nCheck 2nd tile:");
//        System.out.println(board.toString(3, 4));
//
//        // Place 2nd queen
//        board.placeQueen(3, 4);
//        System.out.println("\nBoard with 2 queens:");
//        System.out.println(board.toString());
//        System.out.println("Endangered state of the board:");
//        System.out.println(board.toString_endangered());
        

		BacktrackingBoard board = new BacktrackingBoard(8);
		int[] queenPlacements = { 0, 3, 1, 4, 2 };
		for (int i = 0; i < queenPlacements.length; i++) {
			board.placeQueen(i, queenPlacements[i]);
		}
    }

}
