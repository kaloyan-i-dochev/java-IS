package nqueens.constraints;

import nqueens.boards.SingleDimensionBoard;
import org.junit.jupiter.api.Test;

public class ConstraintSatisfactionSolverTest {
	int repeats = 1000;
    int size = 10;
    int maxIterationsWithoutImprovement = 100;
    int maxResets = 10000;

	@Test
	public void testFirstSolver() {
//	    int size = 10;
//	    int maxIterationsWithoutImprovement = 100;
//	    int maxResets = 100000;

	    ConstraintSatisfactionSolver solver = new ConstraintSatisfactionSolver();

	    long totalDuration = 0;
	    int successfulSolutions = 0;

	    for (int i = 0; i < repeats; i++) {
	        long startTime = System.nanoTime();
	        SingleDimensionBoard solution = solver.solve(size, maxIterationsWithoutImprovement, maxResets);
	        long endTime = System.nanoTime();

	        if (solution.isResolved()) {
	            successfulSolutions++;
	        }

	        long duration = (endTime - startTime) / 1_000_000;
	        totalDuration += duration;
	    }

	    System.out.println("Success rate: " + successfulSolutions / repeats * 100.0 + "%");
	    System.out.println("Average time taken: " + totalDuration / repeats + " ms");
	}

	@Test
	public void testSecondSolver() {
//	    int size = 10;
//	    int maxIterationsWithoutImprovement = 100;
//	    int maxResets = 100000;

	    DomainBoardConstraintSolver solver = new DomainBoardConstraintSolver();

	    long totalDuration = 0;
	    int successfulSolutions = 0;

	    for (int i = 0; i < repeats; i++) {
	        long startTime = System.nanoTime();
	        SingleDimensionBoard solution = solver.solve(size, maxIterationsWithoutImprovement, maxResets);
	        long endTime = System.nanoTime();

	        if (solution.isResolved()) {
	            successfulSolutions++;
	        }

	        long duration = (endTime - startTime) / 1_000_000;
	        totalDuration += duration;
	    }

	    System.out.println("Success rate: " + successfulSolutions / repeats * 100.0 + "%");
	    System.out.println("Average time taken: " + totalDuration / repeats + " ms");
	}


}
