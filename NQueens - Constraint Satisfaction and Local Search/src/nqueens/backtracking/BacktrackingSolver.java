package nqueens.backtracking;

public class BacktrackingSolver {
    private BacktrackingBoard board;

    public BacktrackingSolver(BacktrackingBoard board) {
        this.board = board;
    }

    public void solve() {
        placeQueen(0);
    }

    private boolean placeQueen(int column) {
        if (column >= board.getSize()) {
            return true;  // All queens are placed
        }

        for (int row = 0; row < board.getSize(); row++) {
            if (board.isSafe(row, column)) {
                board.placeQueen(row, column);

                if (placeQueen(column + 1)) {  // Place next queen
                    return true;
                }

                board.removeQueen(row, column);  // Backtrack
            }
        }

        return false;  // No safe place for this queen
    }
}
