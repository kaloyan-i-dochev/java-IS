package PuzzleSolvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Main.Direction;
import Main.PuzzleSolution;
import Main.PuzzleState;

public class BruteForceSolver extends PuzzleSolver {
    private long peakMemoryUsage; // in kilobytes

	public BruteForceSolver(PuzzleState initialState) {
		super(initialState);
//        allPaths = new ArrayList<>();
	}

	@Override
	public PuzzleSolution solve() {
		PuzzleSolution solution = new PuzzleSolution();
		
        List<List<Direction>> allPaths = new ArrayList<>();
        Set<PuzzleState> visitedStates = new HashSet<>();
        List<Direction> currentPath = new ArrayList<>();

        // Measure memory usage before starting the path generation
//        long initialMemoryUsage = getCurrentMemoryUsage();

        // Generate all paths
        generateAllPaths(allPaths, initialState, visitedStates, currentPath);

        // Measure peak memory usage during path generation
//        long peakMemoryUsage = getPeakMemoryUsage();

        solution.setExecutionTime(0L); // Set execution time as 0 for now
        solution.setMemoryUsage(updatePeakMemoryUsage());

        // Solve the puzzle using the generated paths
        List<Direction> shortestPath = findShortestPath(allPaths);

        // Update the execution time
        solution.setExecutionTime(System.currentTimeMillis() - solution.getExecutionTime());

        // Set the final path in the PuzzleSolution
        solution.setPath(shortestPath);
        solution.putMetric("numberOfPathsGenerated", allPaths.size());

        return solution;
    }

	public void generateAllPaths(List<List<Direction>> allPaths, PuzzleState currentState,
			Set<PuzzleState> visitedStates, List<Direction> currentPath) {
		if (visitedStates.contains(currentState)) {
			return; // Avoid cycles
		} else {
			visitedStates.add(currentState);
		}

		allPaths.add(new ArrayList<>(currentPath)); // Add the current path to the list of paths

		for (Direction direction : Direction.values()) {
			if (currentState.canMove(direction)) {
				PuzzleState nextState = currentState.transform(direction);
				currentPath.add(direction);
				updatePeakMemoryUsage();
				generateAllPaths(allPaths, nextState, new HashSet<>(visitedStates), currentPath);
				currentPath.remove(currentPath.size() - 1);
			}
		}
	}

	public List<List<Direction>> getSolutionPaths(PuzzleState initialState, List<List<Direction>> allPaths) {
		List<List<Direction>> solutionPaths = new ArrayList<>();

		for (List<Direction> path : allPaths) {
			PuzzleState currentState = new PuzzleState(initialState.getGrid());
			for (Direction direction : path) {
				if (currentState.canMove(direction)) {
					currentState = currentState.transform(direction);
				}
			}
			if (currentState.isResolved()) {
				solutionPaths.add(path);
			}
		}
		return solutionPaths;
	}

	public List<Direction> findShortestPath(List<List<Direction>> paths) {
		List<Direction> shortestPath = null;
		int shortestLength = Integer.MAX_VALUE;

		for (List<Direction> path : paths) {
			if (path.size() < shortestLength) {
				shortestPath = path;
				shortestLength = path.size();
			}
		}

		return shortestPath != null ? new ArrayList<>(shortestPath) : Collections.emptyList();
	}

//    private long getCurrentMemoryUsage() {
//        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
//    }

    private long updatePeakMemoryUsage() {
        long currentMemoryUsage = getCurrentMemoryUsage();
        if (currentMemoryUsage > peakMemoryUsage) {
            peakMemoryUsage = currentMemoryUsage;
        }
        return peakMemoryUsage;
    }

}
