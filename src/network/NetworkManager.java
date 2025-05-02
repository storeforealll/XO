package network;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class NetworkManager {
    private boolean isHost;
    private String host;
    private int port;
    private MessageHandler messageHandler;
    
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    public NetworkManager(boolean isHost, String host, int port, MessageHandler messageHandler) {
        this.isHost = isHost;
        this.host = host;
        this.port = port;
        this.messageHandler = messageHandler;
    }
    
    public void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                socket = serverSocket.accept();
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                listenForMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket(host, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                listenForMessages();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to connect to server");
            }
        }).start();
    }
    
    private void listenForMessages() {
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    messageHandler.handleMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public void sendMove(int row, int col) {
        if (out != null) {
            out.println("MOVE " + row + " " + col);
        }
    }
    
    public void sendReset() {
        if (out != null) {
            out.println("RESET");
        }
    }
    
    public void close() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public interface MessageHandler {
        void handleMessage(String message);
    }
}