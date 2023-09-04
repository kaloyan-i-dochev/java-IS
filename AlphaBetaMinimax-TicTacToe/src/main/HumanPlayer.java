package main;

import java.util.Scanner;

public class HumanPlayer extends Player {

	private Scanner scanner = new Scanner(System.in);

	public HumanPlayer(char symbol, GameBoard gameBoard) {
		super(symbol, gameBoard);
	}

	@Override
	public Move makeMove() {
		int row, col;
		do {
			System.out.println("Enter row (0-2):");
			row = scanner.nextInt();
			System.out.println("Enter column (0-2):");
			col = scanner.nextInt();
		} while (!gameBoard.isValidMove(row, col));
		if (gameBoard.makeMove(row, col, symbol)) {
			System.out.println(symbol + " plays: " + row + ":" + col);
			return new Move(row, col);
		} else {
			System.out.println("error");
			return null;
		}
	}
}
