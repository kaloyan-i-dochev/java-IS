package nqueens.simulatedannealing.tests;

import nqueens.simulatedannealing.NumberOfConflictingPairs;
import nqueens.simulatedannealing.SimulatedAnnealingBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberOfConflictingPairsTest {

    @Test
    public void testEmptyBoard() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        NumberOfConflictingPairs costFunction = new NumberOfConflictingPairs();
        Assertions.assertEquals(0, costFunction.calculate(board));
    }

    @Test
    public void testSingleQueen() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        board.placeQueen(0, 0);
        NumberOfConflictingPairs costFunction = new NumberOfConflictingPairs();
        Assertions.assertEquals(0, costFunction.calculate(board));
    }

    @Test
    public void testAllQueensSameRow() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        for (int i = 0; i < 8; i++) {
            board.placeQueen(i, 0);
        }
        NumberOfConflictingPairs costFunction = new NumberOfConflictingPairs();
        Assertions.assertEquals(28, costFunction.calculate(board)); // 8 choose 2 = 28
    }

    @Test
    public void testAllQueensSameDiagonal() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        for (int i = 0; i < 8; i++) {
            board.placeQueen(i, i);
        }
        NumberOfConflictingPairs costFunction = new NumberOfConflictingPairs();
        Assertions.assertEquals(28, costFunction.calculate(board)); // 8 choose 2 = 28
    }

    @Test
    public void testNoConflicts() {
        SimulatedAnnealingBoard board = new SimulatedAnnealingBoard(8);
        int[] placements = {0, 4, 7, 5, 2, 6, 1, 3};
        for (int i = 0; i < 8; i++) {
            board.placeQueen(i, placements[i]);
        }
        NumberOfConflictingPairs costFunction = new NumberOfConflictingPairs();
        Assertions.assertEquals(0, costFunction.calculate(board));
    }
}
