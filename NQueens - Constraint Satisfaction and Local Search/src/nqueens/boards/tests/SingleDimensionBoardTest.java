package nqueens.boards.tests;

import nqueens.boards.SingleDimensionBoard;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class SingleDimensionBoardTest {

    private static Stream<Arguments> provideBoardsAndPositions() {
        SingleDimensionBoard board = new SingleDimensionBoard(8);
        int[] queenPlacements = {0, 4, 7, 5, 2, 6, 1, 3};
        for (int i = 0; i < queenPlacements.length; i++) {
            board.placeQueen(i, queenPlacements[i]);
        }

        return Stream.of(
                // Unsafe positions
                Arguments.of(board, 0, 0, true), Arguments.of(board, 1, 4, true), Arguments.of(board, 2, 7, true),
                Arguments.of(board, 3, 5, true), Arguments.of(board, 4, 2, true), Arguments.of(board, 5, 6, true),
                Arguments.of(board, 6, 1, true), Arguments.of(board, 7, 3, true),
                // Safe positions
                Arguments.of(board, 1, 2, false), Arguments.of(board, 2, 1, false), Arguments.of(board, 3, 0, false),
                Arguments.of(board, 4, 6, false), Arguments.of(board, 5, 0, false), Arguments.of(board, 6, 7, false),
                Arguments.of(board, 7, 1, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideBoardsAndPositions")
    public void testIsSafe(SingleDimensionBoard board, int row, int column, boolean expected) {
        Assertions.assertEquals(expected, board.isSafe(row, column),
                "Unexpected result for position (" + row + ", " + column + "). Board:\n" + board.toTestString());
    }

    private static Stream<Arguments> provideBoards() {
        SingleDimensionBoard resolvedBoard = new SingleDimensionBoard(8);
        int[] queenPlacements = {0, 4, 7, 5, 2, 6, 1, 3};
        for (int i = 0; i < queenPlacements.length; i++) {
            resolvedBoard.placeQueen(i, queenPlacements[i]);
        }

        SingleDimensionBoard unresolvedBoard = new SingleDimensionBoard(8);
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
    public void testIsResolved(SingleDimensionBoard board, boolean expected) {
        Assertions.assertEquals(expected, board.isResolved());
    }
}
