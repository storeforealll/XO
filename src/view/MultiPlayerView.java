package view;

import network.NetworkManager;
import viewmodel.MultiPlayerViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPlayerView extends JFrame {
    private MultiPlayerViewModel viewModel;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private NetworkManager networkManager;

    public MultiPlayerView(boolean isHost, int port) {
        this.networkManager = new NetworkManager(isHost, "localhost", port, this::handleNetworkMessage);
        this.viewModel = new MultiPlayerViewModel(networkManager, isHost);
        initializeUI();
        if (isHost) {
            networkManager.startServer();
        } else {
            networkManager.connectToServer();
        }
    }

    public MultiPlayerView(boolean isHost, String ip, int port) {
        this.networkManager = new NetworkManager(isHost, ip, port, this::handleNetworkMessage);
        this.viewModel = new MultiPlayerViewModel(networkManager, isHost);
        initializeUI();
        if (!isHost) {
            networkManager.connectToServer();
        }
    }

    private void initializeUI() {
        setTitle("Tic Tac Toe - Multiplayer");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onButtonClick(row, col);
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }

        String playerSymbol = viewModel.isMyTurn() ? "X" : "O";
        statusLabel = new JLabel("You are Player " + playerSymbol + ". " + 
                                (viewModel.isMyTurn() ? "Your turn" : "Waiting for opponent..."), 
                                SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton resetBtn = new JButton("Reset Game");
        resetBtn.addActionListener(e -> resetGame());
        JButton menuBtn = new JButton("Main Menu");
        menuBtn.addActionListener(e -> returnToMenu());

        controlPanel.add(resetBtn);
        controlPanel.add(menuBtn);

        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);

        updateBoard();
    }

    private void onButtonClick(int row, int col) {
        if (viewModel.isMyTurn() && viewModel.makeMove(row, col)) {
            updateBoard();
            checkGameStatus();
        }
    }

    private void updateBoard() {
        char[][] board = viewModel.getBoardState();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(board[i][j] == '-' ? "" : String.valueOf(board[i][j]));
                buttons[i][j].setEnabled(board[i][j] == '-' && !viewModel.isGameOver() && viewModel.isMyTurn());
            }
        }
        
        String playerSymbol = viewModel.isMyTurn() ? "X" : "O";
        statusLabel.setText("You are Player " + playerSymbol + ". " + 
                          (viewModel.isMyTurn() ? "Your turn" : "Waiting for opponent..."));
    }

    private void checkGameStatus() {
        char winner = viewModel.checkWinner();
        if (winner != '-') {
            String message;
            if (winner == 'D') {
                message = "It's a draw!";
            } else {
                message = "Player " + winner + " wins!";
            }
            statusLabel.setText(message);
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setEnabled(false);
                }
            }
            
            JOptionPane.showMessageDialog(this, message);
        }
    }

    private void resetGame() {
        viewModel.resetGame();
        networkManager.sendReset();
        updateBoard();
    }

    private void returnToMenu() {
        networkManager.close();
        new MainMenuView().setVisible(true);
        dispose();
    }

    private void handleNetworkMessage(String message) {
        if (message.startsWith("MOVE")) {
            String[] parts = message.split(" ");
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);
            SwingUtilities.invokeLater(() -> {
                viewModel.receiveMove(row, col);
                updateBoard();
                checkGameStatus();
            });
        } else if (message.equals("RESET")) {
            SwingUtilities.invokeLater(() -> {
                viewModel.resetGame();
                updateBoard();
            });
        }
    }
}