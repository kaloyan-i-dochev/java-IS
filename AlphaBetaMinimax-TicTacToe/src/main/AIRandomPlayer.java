package main;

public class AIRandomPlayer extends AIPlayer {

	public AIRandomPlayer(char symbol, GameBoard gameBoard) {
		super(symbol, gameBoard);
	}

	@Override
	public Move makeMove() {
		int row, col;
		do {
			row = (int) (Math.random() * GameBoard.SIZE);
			col = (int) (Math.random() * GameBoard.SIZE);
		} while (!gameBoard.isValidMove(row, col));
		if (gameBoard.makeMove(row, col, symbol)) {
			System.out.println(symbol + " plays: " + row + ":" + col);
			return new Move(row, col);
		} else {
			return null;
		}
	}
}
