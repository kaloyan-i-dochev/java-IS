package nqueens.strategic;

import nqueens.boards.SingleDimensionBoard;

public class BacktrackSolver {

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

        for (int col = 0; col < board.getSize(); col++) {
            if (board.isSafe(row, col)) {
                board.placeQueen(row, col);

                if (solveRecursively(board, row + 1)) {
                    return true; // Found a solution
                }

                board.removeQueen(row, col); // Backtrack
            }
        }

        return false; // No solution found for this row
    }
}
