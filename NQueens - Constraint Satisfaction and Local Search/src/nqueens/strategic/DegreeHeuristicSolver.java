package nqueens.strategic;

import nqueens.boards.SingleDimensionBoard;

public class DegreeHeuristicSolver {
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

			int remainingValues = calculateDegree(board, row);
			if (remainingValues < minRemainingValues) {
				minRemainingValues = remainingValues;
				mostConstrainedRow = row;
			}
		}

		return mostConstrainedRow;
	}
	
	public int calculateDegree(SingleDimensionBoard board, int row) {
	    int degree = 0;

	    // Check each tile in the queen's row
	    for (int col = 0; col < board.getSize(); col++) {
	        // Count the number of conflicts if the queen were placed on this tile
	        for (int otherRow = 0; otherRow < board.getSize(); otherRow++) {
	            if (otherRow != row && !board.hasQueen(otherRow) && board.isSafe(otherRow, col)) {
	                degree++;
	            }
	        }
	    }

	    return degree;
	}

}
