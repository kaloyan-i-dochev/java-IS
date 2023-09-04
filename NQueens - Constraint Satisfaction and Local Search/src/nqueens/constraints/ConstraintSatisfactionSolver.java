package nqueens.constraints;

import java.util.Random;

import nqueens.boards.SingleDimensionBoard;

public class ConstraintSatisfactionSolver {
	protected boolean shouldLog = false;
	protected void log(String toLog) {
		if (shouldLog)
			System.out.println(toLog);
	}

	public ConstraintSatisfactionSolver() {
	}

	public SingleDimensionBoard solve(int boardSize, int maxIterationsWithoutImprovement, int maxResets) {
		SingleDimensionBoard board = generateRandomBoard(boardSize);
		int iterationsWithoutImprovement = 0;
		int resets = 0;

		while (iterationsWithoutImprovement < maxIterationsWithoutImprovement) {
			int mostConflictingQueen = getMostConflictingQueen(board);
			if (mostConflictingQueen == -1) {
				log("SOLVED");
	            break;
	        }
			
			int leastConflictingTile = getLeastConflictingTile(board, mostConflictingQueen);

			if (board.getQueen(mostConflictingQueen) != leastConflictingTile) {
				board.placeQueen(mostConflictingQueen, leastConflictingTile);
				iterationsWithoutImprovement = 0;
			} else {
				iterationsWithoutImprovement++;
			}

			if (iterationsWithoutImprovement >= maxIterationsWithoutImprovement) {
				if (resets < maxResets) {
//                	System.out.println("Reset.");
					board = generateRandomBoard(boardSize);
					iterationsWithoutImprovement = 0;
					resets++;
				} else {
					// If we've reached the maximum number of resets, stop trying
					log("EXPIRE");
					break;
				}
			}
		}

		return board;
	}

	private SingleDimensionBoard generateRandomBoard(int size) {
		SingleDimensionBoard board = new SingleDimensionBoard(size);
		Random random = new Random();

		// Place a queen randomly in each row
		for (int row = 0; row < size; row++) {
			int column = random.nextInt(size);
			board.placeQueen(row, column);
		}

		return board;
	}

	public int getMostConflictingQueen(SingleDimensionBoard board) {
		int mostConflictingQueen = -1;
		int maxConflicts = 0;

		for (int i = 0; i < board.getSize(); i++) {
			if (board.hasQueen(i)) {
				int conflicts = getConflicts(board, i);
				if (conflicts > maxConflicts) {
					maxConflicts = conflicts;
					mostConflictingQueen = i;
				}
			}
		}

		return mostConflictingQueen;
	}

	private int getConflicts(SingleDimensionBoard board, int row) {
		int conflicts = 0;
		int queenColumn = board.getQueen(row);

		for (int i = 0; i < board.getSize(); i++) {
			if (i != row && board.hasQueen(i)) {
				int otherQueenColumn = board.getQueen(i);
				if (otherQueenColumn == queenColumn || Math.abs(i - row) == Math.abs(otherQueenColumn - queenColumn)) {
					conflicts++;
				}
			}
		}

		return conflicts;
	}

	private int getLeastConflictingTile(SingleDimensionBoard board, int queenRow) {
		int leastConflictingTile = -1;
		int leastConflicts = Integer.MAX_VALUE;

		// Temporarily remove the queen from the board to avoid self-conflict
		int originalPosition = board.getQueen(queenRow);
		board.removeQueen(queenRow, originalPosition);

		// Check each tile in the queen's row
		for (int col = 0; col < board.getSize(); col++) {
			int conflicts = 0;

			// Count the number of conflicts if the queen were placed on this tile
			for (int row = 0; row < board.getSize(); row++) {
				if (row != queenRow && board.isOccupied(row, col)) {
					conflicts++;
				}
			}

			// If this tile has fewer conflicts than the current least, update the least
			if (conflicts < leastConflicts) {
				leastConflicts = conflicts;
				leastConflictingTile = col;
			}
		}

		// Put the queen back to its original position
		board.placeQueen(queenRow, originalPosition);

		return leastConflictingTile;
	}

}
