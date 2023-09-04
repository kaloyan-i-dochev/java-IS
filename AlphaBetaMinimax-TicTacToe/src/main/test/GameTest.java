package main.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private GameBoard gameBoard;
    private Player player1;
    private Player player2;
    private Game game;

    @BeforeEach
    public void setUp() {
        gameBoard = new GameBoard();
        player1 = new HumanPlayer('X', gameBoard);
        player2 = new AIRandomPlayer('O', gameBoard);
        game = new Game(player1, player2, gameBoard);
    }

    @Test
    public void testGameInitialization() {
        // Check if the game board is initialized with all EMPTY cells
        for (int i = 0; i < GameBoard.SIZE; i++) {
            for (int j = 0; j < GameBoard.SIZE; j++) {
                assertEquals(GameBoard.EMPTY, gameBoard.getCell(i, j));
            }
        }
    }

    @Test
    public void testGameFlow() {
        // Mock a game where both players make predefined moves
        gameBoard.makeMove(0, 0, 'X'); // Player 1
        gameBoard.makeMove(0, 1, 'O'); // Player 2
        gameBoard.makeMove(1, 1, 'X'); // Player 1
        gameBoard.makeMove(1, 0, 'O'); // Player 2
        gameBoard.makeMove(2, 2, 'X'); // Player 1

        // Player 1 should win with a diagonal from top-left to bottom-right
        assertTrue(gameBoard.checkWin('X'));
        assertFalse(gameBoard.checkWin('O'));
    }
}
