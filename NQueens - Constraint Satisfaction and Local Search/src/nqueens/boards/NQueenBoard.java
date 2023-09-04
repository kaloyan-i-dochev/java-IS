package nqueens.boards;

public abstract class NQueenBoard {
	protected int size;
	protected boolean shouldLog = true;

	public NQueenBoard(int size) {
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}

	public abstract void placeQueen(int row, int column);

	public abstract int removeQueen(int row, int column);

	public abstract boolean isSafe(int row, int column);

	public abstract boolean isOccupied(int row, int column);

	public abstract boolean isResolved();

	public abstract boolean hasQueen(int i);

	public abstract int getQueen(int i);

	public int posDiagonalIndex(int row, int column) {
		return row + column;
	}

	public int negDiagonalIndex(int row, int column) {
		return row + ((this.getSize() - 1) - column);
	}

	protected void log(String toLog) {
		if (shouldLog)
			System.out.println(toLog);
	}
}
