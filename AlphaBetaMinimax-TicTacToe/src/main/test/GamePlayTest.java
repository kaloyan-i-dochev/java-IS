package main.test;

import org.junit.jupiter.api.Test;

import main.*;

public class GamePlayTest {

	@Test
	public void testPlayGame() {
		GameBoard sharedGameBoard = new GameBoard();
		Player player1 = new HumanPlayer('X', sharedGameBoard);
		Player player2 = new AlphaBetaAIPlayer('O', sharedGameBoard);
		Game game = new Game(player1, player2, sharedGameBoard);

		game.startGame();
	}
}
