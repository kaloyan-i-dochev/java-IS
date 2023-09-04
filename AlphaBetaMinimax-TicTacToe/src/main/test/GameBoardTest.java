package main.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import main.GameBoard;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTest {
	
    @Test
    public void testInvalidMoveTileTaken() {
        GameBoard board = new GameBoard();

        // Make a move at (1, 1)
        assertTrue(board.makeMove(1, 1, 'X'));

        // Try to make another move at the same position
        assertFalse(board.makeMove(1, 1, 'O'));
    }

    private static Stream<Arguments> provideMoveData() {
        return Stream.of(
            Arguments.of(0, 0, 'X', true),
            Arguments.of(1, 1, 'X', true),
            Arguments.of(2, 2, 'X', true),
            Arguments.of(1, 1, 'O', true),
            Arguments.of(3, 1, 'X', false),
            Arguments.of(1, 3, 'X', false),
            Arguments.of(-1, -1, 'X', false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideMoveData")
    public void testMakeMove(int row, int col, char symbol, boolean expected) {
        GameBoard board = new GameBoard();
        assertEquals(expected, board.makeMove(row, col, symbol));
    }

    private static Stream<Arguments> provideWinData() {
        return Stream.of(
            Arguments.of(new char[][]{{'X', '-', '-'}, {'-', 'X', '-'}, {'-', '-', 'X'}}, 'X', true),
            Arguments.of(new char[][]{{'X', 'X', 'X'}, {'-', '-', '-'}, {'-', '-', '-'}}, 'X', true),
            Arguments.of(new char[][]{{'X', 'O', 'X'}, {'O', 'X', 'O'}, {'X', 'O', 'X'}}, 'X', true),
            Arguments.of(new char[][]{{'O', 'X', 'O'}, {'X', 'O', 'X'}, {'O', 'X', 'O'}}, 'O', true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWinData")
    public void testCheckWin(char[][] initialBoardData, char symbol, boolean expected) {
        GameBoard board = new GameBoard(initialBoardData);
        // Set up the board state
        assertEquals(expected, board.checkWin(symbol));
    }
}
