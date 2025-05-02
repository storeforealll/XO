package network;

import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        clientSocket = serverSocket.accept(); // Wait for client
        System.out.println("Client connected!");
    }

    public void broadcast(String message) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(message);
    }

    public void close() throws IOException {
        serverSocket.close();
        if (clientSocket != null) clientSocket.close();
    }
}