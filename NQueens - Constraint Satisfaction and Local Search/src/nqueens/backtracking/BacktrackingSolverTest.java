package nqueens.backtracking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BacktrackingSolverTest {

    @Test
    public void testSolve() {
        BacktrackingBoard board = new BacktrackingBoard(8);
        BacktrackingSolver solver = new BacktrackingSolver(board);

        solver.solve();

		System.out.println(board.toString_endangered());
        Assertions.assertTrue(board.isResolved(), "Board is not resolved:\n" + board.toString() + "\n" + board.toString_endangered());
    }
}
