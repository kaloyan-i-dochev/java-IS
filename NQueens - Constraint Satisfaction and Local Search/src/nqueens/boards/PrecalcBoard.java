package nqueens.boards;

import java.util.Arrays;

public class PrecalcBoard extends SingleDimensionBoard {
//	protected int[] board;
	protected int[] columnCounts;
	protected int[] posDiagonalCounts;
	protected int[] negDiagonalCounts;

	public PrecalcBoard(int size) {
		super(size);
//		this.board = new int[size];
		this.columnCounts = new int[size];
		this.posDiagonalCounts = new int[2 * size - 1];
		this.negDiagonalCounts = new int[2 * size - 1];
		Arrays.fill(this.board, -1); // Initialize all queens to -1
	}

	public PrecalcBoard(PrecalcBoard board) {
		super(board.getSize());
		this.board = new int[board.getSize()];
		this.columnCounts = new int[board.getSize()];
		this.posDiagonalCounts = new int[2 * board.getSize() - 1];
		this.negDiagonalCounts = new int[2 * board.getSize() - 1];
		for (int i = 0; i < board.getSize(); i++) {
			this.board[i] = board.getQueen(i);
			this.columnCounts[i] = board.columnCounts[i];
			this.posDiagonalCounts[i] = board.posDiagonalCounts[i];
			this.negDiagonalCounts[i] = board.negDiagonalCounts[i];
		}
	}

	@Override
	public void placeQueen(int row, int column) {
		if (hasQueen(row))
			removeQueen(row);
		this.board[row] = column;
		this.columnCounts[column]++;
		this.posDiagonalCounts[posDiagonalIndex(row, column)]++;
		this.negDiagonalCounts[negDiagonalIndex(row, column)]++;
	}

	@Override
	public int removeQueen(int row, int column) {
		return removeQueen(row);
	}

	@Override
	public int removeQueen(int row) {
		int removedQueenColumn = super.removeQueen(row);

		// Update the column counts
		columnCounts[removedQueenColumn]--;

		// Update the diagonal counts
		posDiagonalCounts[posDiagonalIndex(row, removedQueenColumn)]--;
		negDiagonalCounts[negDiagonalIndex(row, removedQueenColumn)]--;

		return removedQueenColumn;
	}

//    @Override
	public void swapQueens(int row1, int row2) {
		int col1 = removeQueen(row1); // Get and remove the queen at row1
		int col2 = removeQueen(row2); // Get and remove the queen at row2

		// Place the queens at the swapped positions
		placeQueen(row1, col2);
		placeQueen(row2, col1);
	}

	@Override
	public boolean isSafe(int row, int column) {
		boolean isOccupied = isOccupied(row, column);
		boolean hasQueen = hasQueen(row);
		boolean safeRow = !hasQueen || (hasQueen && isOccupied);

		int columnCount = columnCounts[column];
		int posDiagonalCount = posDiagonalCounts[posDiagonalIndex(row, column)];
		int negDiagonalCount = negDiagonalCounts[negDiagonalIndex(row, column)];

		if (isOccupied) {
			columnCount--;
			posDiagonalCount--;
			negDiagonalCount--;
		}

		boolean safeColumn = columnCount == 0;
		boolean safePosDiagonal = posDiagonalCount == 0;
		boolean safeNegDiagonal = negDiagonalCount == 0;

		return safeRow && safeColumn && safePosDiagonal && safeNegDiagonal;
	}

	public boolean isSafeToMove(int row, int column) {
		boolean isOccupied = isOccupied(row, column);

		int columnCount = columnCounts[column];
		int posDiagonalCount = posDiagonalCounts[posDiagonalIndex(row, column)];
		int negDiagonalCount = negDiagonalCounts[negDiagonalIndex(row, column)];

		if (isOccupied) {
			columnCount--;
			posDiagonalCount--;
			negDiagonalCount--;
		}

		boolean safeColumn = columnCount == 0;
		boolean safePosDiagonal = posDiagonalCount == 0;
		boolean safeNegDiagonal = negDiagonalCount == 0;

		return safeColumn && safePosDiagonal && safeNegDiagonal;
	}

	public int getConflicts(int row, int col) {
		int colConflicts = getColumnConflicts(col); 
		int posDiagonalConflicts = getPosDiagonalConflicts(row, col);
		int negDiagonalConflicts = getNegDiagonalConflicts(row, col);

		boolean isOccupied = isOccupied(row, col);
		if (isOccupied) {
			colConflicts--;
			posDiagonalConflicts--;
			negDiagonalConflicts--;
		}
		
		int conflicts = colConflicts + posDiagonalConflicts + negDiagonalConflicts;
		return conflicts;
	}

	public int getConflicts(int row) {
		int col = getQueen(row);
		return getConflicts(row, col);
	}

	public int[] getColumnCounts() {
		return columnCounts;
	}

	public int[] getPosDiagonalCounts() {
		return posDiagonalCounts;
	}

	public int[] getNegDiagonalCounts() {
		return negDiagonalCounts;
	}

	public int getColumnConflicts(int col) {
		return columnCounts[col];
	}

	public int getPosDiagonalConflicts(int row, int col) {
		return posDiagonalCounts[posDiagonalIndex(row, col)];
	}

	public int getNegDiagonalConflicts(int row, int col) {
		return negDiagonalCounts[negDiagonalIndex(row, col)];
	}
}
