package main;

import java.util.function.Function;

public class AlphaBetaPruningAlgorithm {
    private int alpha;
    private int beta;
    private char opponent;
    private char currentPlayer;

	private static final int MAX_SCORE = 10;
    private static final int MIN_SCORE = -10;

    public AlphaBetaPruningAlgorithm() {
        reset();
    }

    public int runMinimax(GameBoard board, char currentPlayer, boolean isMaximizing) {
        reset();
        this.currentPlayer = currentPlayer;
        opponent = (currentPlayer == 'X') ? 'O' : 'X';
        return minimax(board, isMaximizing);
    }

    private int minimax(GameBoard board, boolean isMaximizing) {
        int boardVal = evaluateBoard(board);

        if (boardVal == MAX_SCORE) return boardVal;
        if (boardVal == MIN_SCORE) return boardVal;
        if (board.getAvailableMoves().isEmpty()) return 0; // Draw

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : board.getAvailableMoves()) {
                int eval = simulateMove(board, move, currentPlayer, 
                    b -> minimax(b, false));
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break; // Alpha-beta pruning
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : board.getAvailableMoves()) {
                int eval = simulateMove(board, move, opponent, 
                    b -> minimax(b, true));
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break; // Alpha-beta pruning
            }
            return minEval;
        }
    }

    private int simulateMove(GameBoard board, Move move, char symbol, Function<GameBoard, Integer> evalFunction) {
        board.makeMove(move, symbol);
        int score = evalFunction.apply(board);
        board.clearTile(move); // Undo move
        return score;
    }

    private void reset() {
        alpha = Integer.MIN_VALUE;
        beta = Integer.MAX_VALUE;
        currentPlayer = ' ';
    }

    private int evaluateBoard(GameBoard board) {
        if (board.checkWin(currentPlayer)) return MAX_SCORE;
        if (board.checkWin(opponent)) return MIN_SCORE;
        return 0; // Draw or ongoing game
    }
}
