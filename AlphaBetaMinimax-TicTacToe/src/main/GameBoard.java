package main;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private char[][] board;
    public static final int SIZE = 3;
    public static final char EMPTY = '-';

    public GameBoard() {
        board = new char[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // Constructor that accepts an initial state array
    public GameBoard(char[][] initialState) {
        this.board = new char[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                this.board[i][j] = initialState[i][j];
            }
        }
    }

    // Copy constructor
    public GameBoard(GameBoard other) {
        this.board = new char[SIZE][SIZE];
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                this.board[i][j] = other.board[i][j];
            }
        }
    }

    public boolean makeMove(Move move, char symbol) {
        return makeMove(move.getRow(), move.getCol(), symbol);
    }
    
    public boolean makeMove(int row, int col, char symbol) {
        if(isValidMove(row, col)) {
            board[row][col] = symbol;
            return true;
        }
        return false;
    }

    public boolean isValidMove(int row, int col) {
        if(row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return false;
        }
        return board[row][col] == EMPTY;
    }

    // Updated isValidMove method to accept a Move object
    public boolean isValidMove(Move move) {
        return isValidMove(move.getRow(), move.getCol());
    }
    
    public void clearTile(Move move) {
    	int row = move.getRow();
    	int col = move.getCol();
        if(0 <= row && row < SIZE && 0 <= col && col < SIZE) {        	
        	board[move.getRow()][move.getCol()] = EMPTY;
        }
    }

	public char getCell(int i, int j) {
		return board[i][j];
	}
	
	public List<Move> getAvailableMoves() {
	    List<Move> availableMoves = new ArrayList<>();
	    for (int i = 0; i < SIZE; i++) {
	        for (int j = 0; j < SIZE; j++) {
	            if (board[i][j] == EMPTY) {
	                availableMoves.add(new Move(i, j));
	            }
	        }
	    }
	    return availableMoves;
	}

    public boolean checkWin(char symbol) {
        // Check rows and columns
        for(int i = 0; i < SIZE; i++) {
            if(board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) {
                return true;
            }
            if(board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol) {
                return true;
            }
        }

        // Check diagonals
        boolean diagonal1 = true;
        boolean diagonal2 = true;
        for(int i = 0; i < SIZE; i++) {
            diagonal1 &= (board[i][i] == symbol);
            diagonal2 &= (board[i][SIZE - i - 1] == symbol);
        }

        if(diagonal1 || diagonal2) {
            return true;
        }

        return false;
    }

    public void displayBoard() {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                boardString.append(board[i][j]).append(" ");
            }
            boardString.append("\n");  // Append a newline character for each row
        }
        return boardString.toString();
    }
}
