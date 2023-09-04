package nqueens.genetic.tests;

import nqueens.genetic.*;
import nqueens.genetic.solvers.SimpleElitismGeneticSolver;
import nqueens.genetic.solvers.SimpleGeneticSolver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneticSolverTest {

	@Test
	public void testInitialization() {
		SimpleGeneticSolver solver = new SimpleGeneticSolver(8, 100, 1000);

		// There's no direct way to test that the solver was initialized correctly,
		// as all its fields are private. However, we can at least check that it doesn't
		// throw an exception.
		assertNotNull(solver);
	}

	@Test
	public void testSolve() {
		SimpleGeneticSolver solver = new SimpleGeneticSolver(8, 100, 1000);
		GeneticBoard solution = solver.solve();

		// The solution should be a valid N-queens solution
		assertEquals(0, new ConflictingQueensFitnessFunction().calculate(solution));
	}

	@Test
	public void testImprovement_SimpleGeneticSolver() {
		int runs = 100;
		int sum = 0;
		int improvements = 0;
		int solved = 0;
		long startTime = System.nanoTime();
		for (int i = 0; i < runs; i++) {
			long iterationStartTime = System.nanoTime();
			int boardSize = 16;
			int populationSize = 100;
			int maxGenerations = 100;
			SimpleGeneticSolver solver = new SimpleGeneticSolver(boardSize, populationSize, maxGenerations);
			GeneticBoard solution = solver.solve();
			int finalCost = new ConflictingQueensFitnessFunction().calculate(solution);
			long iterationEndTime = System.nanoTime();
			double iterationDuration = (iterationEndTime - iterationStartTime) / 1_000_000_000.0;
//			System.out.println(
//					"Final cost for run " + i + " : " + finalCost + ", Time: " + iterationDuration + " seconds");
//			System.out.println("Solution for run " + i);
			sum += finalCost;
			if (finalCost == 0) {
				solved++;
			}
			if (finalCost < boardSize) { // 8 is the maximum number of conflicts in an 8-queens problem
				improvements++;
			}
		}
		long endTime = System.nanoTime();
		double totalDuration = (endTime - startTime) / 1_000_000_000.0;
		double improvementRate = (double) improvements / runs;
		double solutionRate = (double) solved / runs;
		System.out.println("Total time: " + totalDuration + " seconds, Runs: " + runs);
		System.out.println("improvementRate: " + improvementRate);
		System.out.println("solutionRate: " + solutionRate);
		System.out.println("averagePerformance: " + (double) sum / runs);
		assertTrue(improvementRate > 0.95,
				"The solver should improve the solution in more than 95% of the runs but was " + improvementRate
						+ "%.");
		assertTrue(solutionRate > 0.5,
				"The solver should find a solution in more than 50% of the runs but was " + solutionRate + "%.");
	}

	@Test
	public void testImprovement_SimpleElitismGeneticSolver() {
		int runs = 100;
		int sum = 0;
		int improvements = 0;
		int solved = 0;
		long startTime = System.nanoTime();
		for (int i = 0; i < runs; i++) {
			long iterationStartTime = System.nanoTime();
			int boardSize = 16;
			int populationSize = 100;
			int maxGenerations = 100;
			SimpleElitismGeneticSolver solver = new SimpleElitismGeneticSolver(boardSize, populationSize,
					maxGenerations);
			GeneticBoard solution = solver.solve();
			int finalCost = new ConflictingQueensFitnessFunction().calculate(solution);
			long iterationEndTime = System.nanoTime();
			double iterationDuration = (iterationEndTime - iterationStartTime) / 1_000_000_000.0;
//			System.out.println(
//					"Final cost for run " + i + " : " + finalCost + ", Time: " + iterationDuration + " seconds");
//			System.out.println("Solution for run " + i);
			sum += finalCost;
			if (finalCost == 0) {
				solved++;
			}
			if (finalCost < boardSize) { // 8 is the maximum number of conflicts in an 8-queens problem
				improvements++;
			}
		}
		long endTime = System.nanoTime();
		double totalDuration = (endTime - startTime) / 1_000_000_000.0;
		double improvementRate = (double) improvements / runs;
		double solutionRate = (double) solved / runs;
		System.out.println("Total time: " + totalDuration + " seconds, Runs: " + runs);
		System.out.println("improvementRate: " + improvementRate);
		System.out.println("solutionRate: " + solutionRate);
		System.out.println("averagePerformance: " + (double) sum / runs);
		assertTrue(improvementRate > 0.95,
				"The solver should improve the solution in more than 95% of the runs but was " + improvementRate
						+ "%.");
		assertTrue(solutionRate > 0.5,
				"The solver should find a solution in more than 50% of the runs but was " + solutionRate + "%.");
	}
}
