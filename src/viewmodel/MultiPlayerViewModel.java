package viewmodel;

import model.GameBoard;
import network.NetworkManager;

public class MultiPlayerViewModel extends GameViewModel {
    private NetworkManager networkManager;
    private boolean myTurn;

    public MultiPlayerViewModel(NetworkManager networkManager, boolean isHost) {
        super();
        this.networkManager = networkManager;
        this.myTurn = isHost; // Host plays first (X)
    }

    @Override
    public boolean makeMove(int row, int col) {
        if (myTurn && super.makeMove(row, col)) {
            networkManager.sendMove(row, col);
            myTurn = false;
            return true;
        }
        return false;
    }

    public void receiveMove(int row, int col) {
        super.makeMove(row, col);
        myTurn = true;
    }

    public boolean isMyTurn() {
        return myTurn;
    }
}