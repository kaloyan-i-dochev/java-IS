package PuzzleSolvers.Patterns;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;

import Main.Direction;
import Main.PuzzleState;
import Main.PuzzleState.PuzzleStateAux;

public class PatternDatabase {
    private Map<PuzzleState, Integer> database;
    private int numRows;
    private int numCols;
    private PuzzleState goalState;

    public PatternDatabase(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.database = new HashMap<>();
        int[][] goalGrid = generateDefaultGoalState(this.numRows, this.numCols);
        this.goalState = new PuzzleState(goalGrid);
    }

    public PatternDatabase(PuzzleState customGoalState) {
        this.numRows = customGoalState.getGrid().length;
        this.numCols = customGoalState.getGrid()[0].length;
        this.database = new HashMap<>();
        this.goalState = customGoalState;
    }

    private int[][] generateDefaultGoalState(int rows, int cols) {
        int[][] goalGrid = new int[rows][cols];
        int count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                goalGrid[i][j] = count % (rows * cols);
                count++;
            }
        }
        return goalGrid;
    }

    public void generateDatabase() {
        // Start from the goal state
        Queue<PuzzleState> queue = new LinkedList<>();
        queue.add(goalState);
        database.put(goalState, 0);

        while (!queue.isEmpty()) {
            PuzzleState current = queue.poll();
            int currentCost = database.get(current);

            // Generate all possible next states
            List<PuzzleState> nextStates = generateNextStates(current);

            for (PuzzleState nextState : nextStates) {
                if (!database.containsKey(nextState)) {
                    // Compute the cost for the next state
                    int nextCost = currentCost + 1;
                    database.put(nextState, nextCost);
                    queue.add(nextState);
                }
            }
        }
    }

    public int queryDatabase(PuzzleState state) {
        return database.getOrDefault(state, Integer.MAX_VALUE);
    }

    private List<PuzzleState> generateNextStates(PuzzleState state) {
        List<PuzzleState> nextStates = new ArrayList<>();
        int[][] grid = state.getGrid();
        int blankRow = state.getBlankRow();
        int blankCol = state.getBlankCol();
        int numRows = grid.length;
        int numCols = grid[0].length;

        // Generate next states by moving the blank tile in all possible directions
        for (Direction direction : Direction.values()) {
            int newRow = blankRow + direction.getRowOffset();
            int newCol = blankCol + direction.getColOffset();

            if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols) {
                int[][] newGrid = state.copyState();
                PuzzleStateAux.swapTiles(newGrid, blankRow, blankCol, newRow, newCol);

                PuzzleState nextState = new PuzzleState(newGrid, newRow, newCol);
                nextStates.add(nextState);
            }
        }

        return nextStates;
    }
    
    public long getDatabaseSize() {
        return database.size();
    }

	public PuzzleState getGoalState() {
		return goalState;
	}
}
