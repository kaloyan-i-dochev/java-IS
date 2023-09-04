package nqueens.strategic;

import nqueens.boards.SingleDimensionBoard;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;

public class StrategicSolverTest {
	final static int algoCount = 6;

	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 64, 128, 256, 512, 1024 }) // Add more sizes as needed
	@Order(algoCount - 5)
	public void testCombined_MRV_DH_LCV_Precalc_Solver(int size) {
//	    int size = 10; // Size of the chess board

		Combined_MRV_DH_LCV_Precalc_Solver solver = new Combined_MRV_DH_LCV_Precalc_Solver();
	    long startTime = System.nanoTime();
	    SingleDimensionBoard solution = solver.solve(size);
	    long endTime = System.nanoTime();

	    // Check if the solution is valid
//	    System.out.println(solution);
	    assertTrue(solution.isResolved(), "The solution is not valid");

		long durationns = (endTime - startTime) / (1_000); // Convert to nanoseconds
		long durationms = (endTime - startTime) / (1_000_000); // Convert to milliseconds
		if (durationms == 0) {
			System.out.println("Solving a " + size + "x" + size + " board with Combined took " + durationns + " ns");
		} else {
			System.out.println("Solving a " + size + "x" + size + " board with Combined took " + durationms + " ms");

		}
	}

	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 64, 128, 256 }) // Add more sizes as needed
	@Order(algoCount - 4)
	public void testCombined_MRV_DH_LCV_Solver(int size) {
//	    int size = 10; // Size of the chess board

		Combined_MRV_DH_LCV_Solver solver = new Combined_MRV_DH_LCV_Solver();
	    long startTime = System.nanoTime();
	    SingleDimensionBoard solution = solver.solve(size);
	    long endTime = System.nanoTime();

	    // Check if the solution is valid
//	    System.out.println(solution);
	    assertTrue(solution.isResolved(), "The solution is not valid");

		long durationns = (endTime - startTime) / (1_000); // Convert to nanoseconds
		long durationms = (endTime - startTime) / (1_000_000); // Convert to milliseconds
		if (durationms == 0) {
			System.out.println("Solving a " + size + "x" + size + " board with Combined took " + durationns + " ns. Visited " + solver.visitedStates.size() + " states.");
		} else {
			System.out.println("Solving a " + size + "x" + size + " board with Combined took " + durationms + " ms. Visited " + solver.visitedStates.size() + " states.");
		}
		
	}

	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 64, 128 }) // Add more sizes as needed
	@Order(algoCount - 3)
	public void testLCVSolver(int size) {
//	    int size = 10; // Size of the chess board

		LCVSolver solver = new LCVSolver();
	    long startTime = System.nanoTime();
	    SingleDimensionBoard solution = solver.solve(size);
	    long endTime = System.nanoTime();

	    // Check if the solution is valid
	    assertTrue(solution.isResolved(), "The solution is not valid");

		long durationns = (endTime - startTime) / (1_000); // Convert to nanoseconds
		long durationms = (endTime - startTime) / (1_000_000); // Convert to milliseconds
		if (durationms == 0) {
			System.out.println("Solving a " + size + "x" + size + " board with LCV took " + durationns + " ns");
		} else {
			System.out.println("Solving a " + size + "x" + size + " board with LCV took " + durationms + " ms");

		}
	}

	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 64, 128 }) // Add more sizes as needed
	@Order(algoCount - 2)
	public void testDHSolver(int size) {
//	    int size = 10; // Size of the chess board

		DegreeHeuristicSolver solver = new DegreeHeuristicSolver();
	    long startTime = System.nanoTime();
	    SingleDimensionBoard solution = solver.solve(size);
	    long endTime = System.nanoTime();

	    // Check if the solution is valid
	    assertTrue(solution.isResolved(), "The solution is not valid");

		long durationns = (endTime - startTime) / (1_000); // Convert to nanoseconds
		long durationms = (endTime - startTime) / (1_000_000); // Convert to milliseconds
		if (durationms == 0) {
			System.out.println("Solving a " + size + "x" + size + " board with DH took " + durationns + " ns");
		} else {
			System.out.println("Solving a " + size + "x" + size + " board with DH took " + durationms + " ms");

		}
	}

	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 64, 128 }) // Add more sizes as needed
	@Order(algoCount - 1)
	public void testMRVSolver(int size) {
//	    int size = 10; // Size of the chess board

	    MRVSolver solver = new MRVSolver();
	    long startTime = System.nanoTime();
	    SingleDimensionBoard solution = solver.solve(size);
	    long endTime = System.nanoTime();

	    // Check if the solution is valid
	    assertTrue(solution.isResolved(), "The solution is not valid");

		long durationns = (endTime - startTime) / (1_000); // Convert to nanoseconds
		long durationms = (endTime - startTime) / (1_000_000); // Convert to milliseconds
		if (durationms == 0) {
			System.out.println("Solving a " + size + "x" + size + " board with MRV took " + durationns + " ns");
		} else {
			System.out.println("Solving a " + size + "x" + size + " board with MRV took " + durationms + " ms");

		}
	}

	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30 }) // Add more sizes as needed
	@Order(algoCount - 0)
	public void testBacktrackSolver(int size) {
//	    int size = 10; // Size of the chess board

	    BacktrackSolver solver = new BacktrackSolver();
	    long startTime = System.nanoTime();
	    SingleDimensionBoard solution = solver.solve(size);
	    long endTime = System.nanoTime();

	    // Check if the solution is valid
	    assertTrue(solution.isResolved(), "The solution is not valid");

		long durationns = (endTime - startTime) / (1_000); // Convert to nanoseconds
		long durationms = (endTime - startTime) / (1_000_000); // Convert to milliseconds
		if (durationms == 0) {
			System.out.println("Solving a " + size + "x" + size + " board with Backtracking took " + durationns + " ns");
		} else {
			System.out.println("Solving a " + size + "x" + size + " board with Backtracking took " + durationms + " ms");

		}
	}

}
