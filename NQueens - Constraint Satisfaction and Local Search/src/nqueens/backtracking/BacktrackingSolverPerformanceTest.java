package nqueens.backtracking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BacktrackingSolverPerformanceTest {
	@ParameterizedTest
	@ValueSource(ints = { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
			29, 30, 31, 32, 64, 128 }) // Add more sizes as needed
	public void testPerformance(int size) {
//		int n = 16;
		long startTime = System.nanoTime();

//		for (int i = 0; i < n; i++) {
			BacktrackingBoard board = new BacktrackingBoard(size);
			BacktrackingSolver solver = new BacktrackingSolver(board);

			solver.solve();
//		}

		long endTime = System.nanoTime();

		long durationns = (endTime - startTime) / (1_000); // Convert to nanoseconds
		long durationms = (endTime - startTime) / (1_000_000); // Convert to milliseconds
		if (durationms == 0) {
			System.out.println("Solving a " + size + "x" + size + " board took " + durationns + " ns");
		} else {
			System.out.println("Solving a " + size + "x" + size + " board took " + durationms + " ms");

		}

		// Add more performance metrics as needed
	}
}
