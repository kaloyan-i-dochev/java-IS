package nqueens.simulatedannealing;

import nqueens.boards.SingleDimensionBoard;

public class SimulatedAnnealingBoard extends SingleDimensionBoard {

    public SimulatedAnnealingBoard(int size) {
        super(size);
    }

	public SimulatedAnnealingBoard(SimulatedAnnealingBoard board) {
		super(board);
	}
}
