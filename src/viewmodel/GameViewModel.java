package viewmodel;

import model.GameBoard;

public abstract class GameViewModel {
    protected GameBoard gameBoard;

    public GameViewModel() {
        gameBoard = new GameBoard();
    }

    public boolean makeMove(int row, int col) {
        return gameBoard.makeMove(row, col);
    }

    public char checkWinner() {
        return gameBoard.checkWinner();
    }

    public char getCurrentPlayer() {
        return gameBoard.getCurrentPlayer();
    }

    public char[][] getBoardState() {
        return gameBoard.getBoard();
    }

    public void resetGame() {
        gameBoard.resetGame();
    }

    public boolean isGameOver() {
        return gameBoard.isGameOver();
    }
}