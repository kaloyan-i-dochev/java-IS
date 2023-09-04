package nqueens.boards;

import java.util.Arrays;

public class SingleDimensionBoard extends NQueenBoard {
    protected int[] board;

    public SingleDimensionBoard(int size) {
        super(size);
        this.board = new int[size];
        Arrays.fill(this.board, -1); // Initialize all queens to -1
    }

    public SingleDimensionBoard(SingleDimensionBoard board) {
    	super(board.getSize());
        this.board = new int[board.getSize()];
        for (int i = 0; i < board.getSize(); i++) {
            this.board[i] = board.getQueen(i);
        }
	}

	@Override
    public void placeQueen(int row, int column) {
        this.board[row] = column;
    }

	@Override
	public int removeQueen(int row, int column) {
	    return removeQueen(row);
	}

	public int removeQueen(int row) {
	    int removedQueenColumn = this.board[row];
	    this.board[row] = -1;
	    return removedQueenColumn;
	}
    
    public int getQueen(int row) {
    	return this.board[row];
    }
    
    public boolean hasQueen(int row) {
    	return this.board[row] >= 0;
    }

//    @Override
    public void swapQueens(int row1, int row2) {
        int temp = board[row1];
        board[row1] = board[row2];
        board[row2] = temp;
    }

    @Override
    public boolean isSafe(int row, int column) {
        // Check for conflicts with already-placed queens
        for (int currRow = 0; currRow < this.board.length; currRow++) {
            int currCol = this.board[currRow];
            boolean samePosition = currRow == row && currCol == column;
            boolean queenPlaced = currCol >= 0;
            boolean sameRow = currRow == row;
            boolean sameColumn = currCol == column;
            boolean sameDiagonalUp = Math.abs(currRow - row) == Math.abs(currCol - column);
            boolean sameDiagonalDown = Math.abs(currRow - row) == Math.abs(currCol - column);

            if (!samePosition && queenPlaced && (sameRow || sameColumn || sameDiagonalUp || sameDiagonalDown)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isOccupied(int row, int column) {
        return this.board[row] == column;
    }

    @Override
    public boolean isResolved() {
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i] < 0 || !isSafe(i, this.board[i])) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SingleDimensionBoard other = (SingleDimensionBoard) obj;
        return Arrays.equals(board, other.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
    
    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String lineSeparator = "+-".repeat(this.board.length) + "+\n";

		sb.append(lineSeparator);
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				sb.append("|");
				if (this.board[i] == j) {
					sb.append("Q");
				} else {
					sb.append(" ");
				}
			}
			sb.append("|\n");
			sb.append(lineSeparator);
		}
		return sb.toString();
	}

	public String toTestString() {
		StringBuilder sb = new StringBuilder();
		String lineSeparator = "--".repeat(this.board.length * 2 - 1) + "\n";

		sb.append(lineSeparator);
		for (int i = 0; i < this.board.length; i++) {
			sb.append("|");
			for (int j = 0; j < this.board.length; j++) {
				if (this.board[i] == j) {
					sb.append(" Q ");
				} else {
					sb.append("     ");
				}
				sb.append("|");
			}
			sb.append("\n");
			sb.append(lineSeparator);
		}
		return sb.toString();
	}

	public String toString(int endangeredRow, int endangeredColumn) {
		StringBuilder sb = new StringBuilder();
		String lineSeparator = "+-".repeat(this.board.length) + "+\n";

		sb.append(lineSeparator);
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				sb.append("|");
				if (i == endangeredRow && j == endangeredColumn) {
					if (this.board[i] == j) {
						sb.append("A");
					} else {
						sb.append("X");
					}
				} else {
					boolean isEndangered = i == endangeredRow || j == endangeredColumn
							|| Math.abs(i - endangeredRow) == Math.abs(j - endangeredColumn);
					if (this.board[i] == j) {
						if (isEndangered) {
							sb.append("H");
						} else {
							sb.append("Q");
						}
					} else {
						if (isEndangered) {
							sb.append("x");
						} else {
							sb.append(" ");
						}
					}
				}
			}
			sb.append("|\n");
			sb.append(lineSeparator);
		}
		return sb.toString();
	}

	public String toString_endangered() {
		StringBuilder sb = new StringBuilder();
		String lineSeparator = "+-".repeat(this.board.length) + "+\n";

		sb.append(lineSeparator);
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				sb.append("|");
				boolean isEndangered = false;
				for (int k = 0; k < this.board.length; k++) {
					if (this.board[k] >= 0 && !(k == i && this.board[k] == j)
							&& (this.board[k] == j || k == i || Math.abs(k - i) == Math.abs(this.board[k] - j))) {
						isEndangered = true;
						break;
					}
				}
				if (this.board[i] == j) {
					if (isEndangered) {
						sb.append("A");
					} else {
						sb.append("Q");
					}
				} else {
					if (isEndangered) {
						sb.append("x");
					} else {
						sb.append(" ");
					}
				}
			}
			sb.append("|\n");
			sb.append(lineSeparator);
		}
		return sb.toString();
	}
}
