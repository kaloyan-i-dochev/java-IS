package main;

import java.util.Scanner;

public class Game {
	private Scanner scanner = new Scanner(System.in);
	private GameBoard gameBoard;
	private Player player1;
	private Player player2;
	private Player currentPlayer;

	public Game(Player player1, Player player2, GameBoard sharedGameBoard) {
		this.gameBoard = sharedGameBoard;
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = player1; // Player 1 starts the game
	}

	public void startGame() {
		gameBoard.displayBoard();
		while (!checkEndGame()) {
			currentPlayer.makeMove();
			gameBoard.displayBoard();
			if (checkEndGame()) {
				break;
			}
			System.out.println("Press Enter to continue...");
			scanner.nextLine();
			switchTurns();
		}
		System.out.println();
		if (gameBoard.checkWin('X')) {
			System.out.println("X WINS");
		} else if (gameBoard.checkWin('O')) {
			System.out.println("O WINS");
		} else {
			System.out.println("NO ONE WINS");
		}
		gameBoard.displayBoard(); // Display the final state of the board
	}

	private void switchTurns() {
		currentPlayer = (currentPlayer == player1) ? player2 : player1;
	}

	private boolean checkEndGame() {
		return gameBoard.checkWin('X') || gameBoard.checkWin('O') || gameBoard.getAvailableMoves().size() == 0;
	}
}
