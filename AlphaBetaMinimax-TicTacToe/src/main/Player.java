package main;

public abstract class Player {
    protected char symbol;
    protected GameBoard gameBoard;

    public Player(char symbol, GameBoard gameBoard) {
        this.symbol = symbol;
        this.gameBoard = gameBoard;
    }

    public char getSymbol() {
        return symbol;
    }

    public abstract Move makeMove();
}
