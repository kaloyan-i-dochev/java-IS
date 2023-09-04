package PuzzleSolvers;

import Main.PuzzleSolution;
import Main.PuzzleState;
import Main.PuzzleState.PuzzleStateAux;

public abstract class PuzzleSolver {
    protected PuzzleState initialState;

    public PuzzleSolver(PuzzleState initialState) {
        this.initialState = initialState;
    }

    public abstract PuzzleSolution solve();

    protected boolean isSolvable() {
        // Default implementation
        return PuzzleStateAux.isSolvable(initialState);
    }

    protected long getCurrentMemoryUsage() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;
    }
}

