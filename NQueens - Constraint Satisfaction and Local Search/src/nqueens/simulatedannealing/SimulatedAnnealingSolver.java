package nqueens.simulatedannealing;

import java.util.Random;

public class SimulatedAnnealingSolver {
	private SimulatedAnnealingBoard board;
	private CostFunction costFunction;
	private Random random = new Random();

	public SimulatedAnnealingSolver(SimulatedAnnealingBoard board, CostFunction costFunction) {
		this.board = board;
		this.costFunction = costFunction;
	}

	public void solve() {
		double initialTemperature = 10.0;
		int maxAttempts = 10;
		double initialCoolingRate = 0.003;
		double coolingRateIncreaseFactor = 0.9;
		solve(initialTemperature, initialCoolingRate, maxAttempts, coolingRateIncreaseFactor);
	}

	public void solve(double initialTemperature, double initialCoolingRate, int maxAttempts,
			double coolingRateIncreaseFactor) {
		SimulatedAnnealingBoard bestBoard = new SimulatedAnnealingBoard(board);
		int bestCost = Integer.MAX_VALUE;

		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			if (board.isResolved())
				break;

			// Reset the board for each attempt
			for (int i = 0; i < board.getSize(); i++) {
				if (!board.hasQueen(i)) {
					board.placeQueen(i, random.nextInt(board.getSize()));
				}
			}

			double temperature = initialTemperature;
			double coolingRate = initialCoolingRate * Math.pow(coolingRateIncreaseFactor, attempt);

			coolingRate = Math.max(coolingRate, Double.MIN_VALUE); // Ensure cooling rate doesn't go below a certain
																	// threshold
			coolingRate = Math.min(coolingRate, 1); // Ensure cooling rate doesn't go above 1

			while (temperature > 1) {
				if (board.isResolved())
					break;

				// Create a new neighbor board by swapping two queens
				SimulatedAnnealingBoard neighbor = new SimulatedAnnealingBoard(board);
				int row1 = random.nextInt(board.getSize());
				int row2 = random.nextInt(board.getSize());
				neighbor.swapQueens(row1, row2);

				// Calculate cost difference
				int currentCost = costFunction.calculate(board);
				int neighborCost = costFunction.calculate(neighbor);
				int costDifference = neighborCost - currentCost;

				// Decide if we should move to the neighbor
				if (costDifference < 0) {
					board = neighbor;
				} else {
					// Calculate acceptance probability
					double acceptanceProbability = Math.exp(-costDifference / temperature);
					if (random.nextDouble() < acceptanceProbability) {
						board = neighbor;
					}
				}

				// Cool down
				temperature *= 1 - coolingRate;
			}

			// Check if this attempt is better than the best found so far
			int finalCost = costFunction.calculate(board);
			if (finalCost < bestCost) {
				bestCost = finalCost;
				bestBoard = new SimulatedAnnealingBoard(board); // Save a copy of the best board
			}
		}

		// Set the board to the best found solution
		board = bestBoard;
	}

	public SimulatedAnnealingBoard getBoard() {
		return this.board;
	}

}
