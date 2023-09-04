package main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.AlphaBetaAIPlayer;
import main.GameBoard;

public class AlphaBetaPruningAlgorithmTest {
	@Test
	public void testBasicWinCondition() {
	    GameBoard board = new GameBoard();
	    // Set up board state where AI is one move away from winning
	    board.makeMove(0, 0, 'X');
	    board.makeMove(0, 1, 'X');
	    // ... any other necessary moves ...

	    AlphaBetaAIPlayer aiPlayer = new AlphaBetaAIPlayer('X', board);
	    aiPlayer.makeMove();

	    assertTrue(board.checkWin('X')); // Assert that the AI chose the winning move
	}

    @Test
    public void testBasicBlockOpponent() {
        GameBoard board = new GameBoard();
        // Set up board state where the opponent is one move away from winning
        board.makeMove(0, 0, 'O');
        board.makeMove(0, 1, 'O');
        // ... any other necessary moves ...

        AlphaBetaAIPlayer aiPlayer = new AlphaBetaAIPlayer('X', board);
        aiPlayer.makeMove();

        // Check that the AI blocked the winning move for the opponent
        assertEquals('X', board.getCell(0, 2));
    }

}
