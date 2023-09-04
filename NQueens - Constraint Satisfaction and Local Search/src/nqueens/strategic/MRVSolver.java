package nqueens.strategic;

import nqueens.boards.SingleDimensionBoard;

public class MRVSolver {
	public SingleDimensionBoard solve(int size) {
		SingleDimensionBoard board = new SingleDimensionBoard(size);
		if (solveRecursively(board, 0)) {
			return board;
		} else {
			return null; // No solution found
		}
	}

	private boolean solveRecursively(SingleDimensionBoard board, int row) {
		if (row == board.getSize()) {
			return true; // All queens are placed successfully
		}

		int nextRow = selectNextRow(board); // Using MRV to select the next row

		for (int col = 0; col < board.getSize(); col++) {
			if (board.isSafe(nextRow, col)) {
				board.placeQueen(nextRow, col);

				if (solveRecursively(board, row + 1)) {
					return true; // Found a solution
				}

				board.removeQueen(nextRow, col); // Backtrack
			}
		}

		return false; // No solution found for this row
	}

	public int selectNextRow(SingleDimensionBoard board) {
		int mostConstrainedRow = -1;
		int minRemainingValues = Integer.MAX_VALUE;

		for (int row = 0; row < board.getSize(); row++) {
			if (board.hasQueen(row))
				continue; // Skip rows that already have queens

			int remainingValues = countRemainingValues(board, row);
			if (remainingValues < minRemainingValues) {
				minRemainingValues = remainingValues;
				mostConstrainedRow = row;
			}
		}

		return mostConstrainedRow;
	}

	public int countRemainingValues(SingleDimensionBoard board, int row) {
		int count = 0;
		for (int col = 0; col < board.getSize(); col++) {
			if (board.isSafe(row, col))
				count++;
		}
		return count;
	}

}
