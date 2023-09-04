package nqueens.strategic;

import java.util.Arrays;

import nqueens.boards.SingleDimensionBoard;

public class LCVSolver {

    public SingleDimensionBoard solve(int size) {
        SingleDimensionBoard board = new SingleDimensionBoard(size);
        if (solve(0, size, board)) {
            return board;
        }
        return null; // No solution found
    }

    private boolean solve(int row, int size, SingleDimensionBoard board) {
        if (row == size) {
            return true; // All queens are placed successfully
        }

        Integer[] columns = getLeastConstrainingValues(row, size, board);
        for (int col : columns) {
            if (board.isSafe(row, col)) {
                board.placeQueen(row, col);
                if (solve(row + 1, size, board)) {
                    return true; // Found a solution
                }
                board.removeQueen(row, col); // Backtrack
            }
        }
        return false; // No solution found for this row
    }

    private int calculateConstraints(int row, int col, int size, SingleDimensionBoard board) {
        int constraints = 0;
        for (int i = 0; i < size; i++) {
            if (i != row && board.isSafe(i, col)) {
                constraints++;
            }
            if (i != col && board.isSafe(row, i)) {
                constraints++;
            }
        }
        return constraints;
    }

    private Integer[] getLeastConstrainingValues(int row, int size, SingleDimensionBoard board) {
        Integer[] columns = new Integer[size];
        for (int i = 0; i < size; i++) {
            columns[i] = i;
        }
        Arrays.sort(columns, (col1, col2) -> {
            int constraints1 = calculateConstraints(row, col1, size, board);
            int constraints2 = calculateConstraints(row, col2, size, board);
            return Integer.compare(constraints1, constraints2);
        });
        return columns;
    }
}

