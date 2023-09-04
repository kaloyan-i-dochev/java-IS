package nqueens.strategic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nqueens.boards.SingleDimensionBoard;

public class Combined_MRV_DH_LCV_Solver {
	public Set<SingleDimensionBoard> visitedStates = new HashSet<>();

	public SingleDimensionBoard solve(int size) {
		SingleDimensionBoard board = new SingleDimensionBoard(size);
		if (solveRecursively(board, 0)) {
			return board;
		}
		return null; // Return null if no solution is found
	}

	private boolean solveRecursively(SingleDimensionBoard board, int row) {
		if (row == board.getSize()) {
			return true;
		}

		if (visitedStates.contains(board)) {
			System.out.println("Revisited state: " + board);
			// Handle or log as needed
		}
		visitedStates.add(new SingleDimensionBoard(board));
	    System.out.println(visitedStates.size());

		int nextRow = selectNextRow(board);
		if (nextRow == -1) {
			System.out.println("NO NEXT");
			return false;
		}

		Integer[] leastConstrainingValues = getLeastConstrainingValues(nextRow, board.getSize(), board);

		for (int col : leastConstrainingValues) {
			if (board.isSafe(nextRow, col) && !board.isOccupied(nextRow, col)) {
				board.placeQueen(nextRow, col); // Place queen

				if (solveRecursively(board, row + 1)) {
					return true; // Recursively solve the rest of the board
				}

				board.removeQueen(nextRow, col); // Remove queen if not leading to a solution
			}
		}

		return false;
	}

	// Implement MRV and Degree Heuristic
	private int selectNextRow(SingleDimensionBoard board) {
		int size = board.getSize();
		int selectedRow = -1;
		int minValues = Integer.MAX_VALUE;
		int selectedDegree = Integer.MIN_VALUE;

		for (int row = 0; row < size; row++) {
			if (board.hasQueen(row))
				continue; // Skip rows with queens

			int remainingValues = countRemainingValues(row, size, board);

			if (remainingValues < minValues) {
				selectedRow = row;
				minValues = remainingValues;
				selectedDegree = Integer.MIN_VALUE; // Reset maxDegree as it's no longer relevant
			} else if (remainingValues == minValues) {
				if (selectedDegree == Integer.MIN_VALUE) {
					selectedDegree = calculateDegree(selectedRow, size, board); // Calculate degree for the current
																				// selected row
				}
				int degree = calculateDegree(row, size, board); // Calculate degree for the current row
				if (degree > selectedDegree) {
					selectedRow = row;
					selectedDegree = degree;
				}
			}
		}

		return selectedRow;
	}

	// Implement LCV
	private Integer[] getLeastConstrainingValues(int row, int size, SingleDimensionBoard board) {
		Map<Integer, Integer> columnConstraints = new HashMap<>();

		for (int col = 0; col < size; col++) {
			if (!board.isSafe(row, col) || board.isOccupied(row, col))
				continue;
//			else if (!board.isOccupied(row, col))
//				continue;

			board.placeQueen(row, col);
			int constraints = 0;
			for (int nextRow = 0; nextRow < size; nextRow++) {
				if (nextRow == row || board.hasQueen(nextRow))
					continue;

				if (!board.isSafe(nextRow, col))
					constraints++;
			}
			board.removeQueen(row, col);

			columnConstraints.put(col, constraints);
		}

		return columnConstraints.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
				.toArray(Integer[]::new);
	}

	private int countRemainingValues(int row, int size, SingleDimensionBoard board) {
		int count = 0;
		for (int col = 0; col < size; col++) {
			if (board.isSafe(row, col)) {
				count++;
			}
		}
		return count;
	}

	private int calculateDegree(int row, int size, SingleDimensionBoard board) {
		int degree = 0;
		for (int col = 0; col < size; col++) {
			if (!board.isSafe(row, col) || board.isOccupied(row, col))
				continue;
//			if (!board.isSafe(row, col) && !board.isOccupied(row, col))
//				continue;
			board.placeQueen(row, col); // Simulate placing the queen
			for (int nextRow = 0; nextRow < size; nextRow++) {
				if (nextRow == row || board.hasQueen(nextRow))
					continue;

				if (!board.isSafe(nextRow, col))
					degree++;
			}
			board.removeQueen(row, col); // Remove the simulated queen
		}
		return degree;
	}
}
