package nqueens.simulatedannealing;

public class NumberOfConflictingPairs implements CostFunction {
	@Override
	public int calculate(SimulatedAnnealingBoard board) {
	    int conflictingPairs = 0;
	    int size = board.getSize();

	    // Check each pair of queens
	    for (int i = 0; i < size; i++) {
	        if (!board.hasQueen(i)) continue; // Skip if no queen in this row

	        for (int j = i + 1; j < size; j++) {
	            if (!board.hasQueen(j)) continue; // Skip if no queen in this row

	            int queen1 = board.getQueen(i);
	            int queen2 = board.getQueen(j);

	            // Check if queens are on the same row
	            if (queen1 == queen2) {
	                conflictingPairs++;
	            }

	            // Check if queens are on the same diagonal
	            if (Math.abs(queen1 - queen2) == Math.abs(i - j)) {
	                conflictingPairs++;
	            }
	        }
	    }

	    return conflictingPairs;
	}

}
