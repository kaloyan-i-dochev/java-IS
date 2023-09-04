package main;

public class AlphaBetaAIPlayer extends AIPlayer {
	private AlphaBetaPruningAlgorithm algorithm;

	public AlphaBetaAIPlayer(char symbol, GameBoard gameBoard) {
		super(symbol, gameBoard);
		this.algorithm = new AlphaBetaPruningAlgorithm();
	}

	@Override
	public Move makeMove() {
		Move bestMove = findBestMove(gameBoard, this.symbol);
		if (gameBoard.makeMove(bestMove.getRow(), bestMove.getCol(), this.symbol)) {
			return bestMove;
		} else {
			return null;
		}
	}

	private Move findBestMove(GameBoard board, char currentPlayer) {
		int bestScore = Integer.MIN_VALUE;
		Move bestMove = null;

		for (Move move : board.getAvailableMoves()) {
			int moveScore = algorithm.runMinimax(board, currentPlayer, true);
			if (moveScore > bestScore) {
				bestScore = moveScore;
				bestMove = move;
			}
		}
		return bestMove;
	}
}
