package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkConfigView extends JFrame {
    public NetworkConfigView() {
        setTitle("Multiplayer Configuration");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton hostBtn = new JButton("Host Game");
        JButton joinBtn = new JButton("Join Game");
        JButton backBtn = new JButton("Back to Menu");

        hostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String portStr = JOptionPane.showInputDialog("Enter port number:");
                try {
                    int port = Integer.parseInt(portStr);
                    // Start server and wait for connection
                    new MultiPlayerView(true, port).setVisible(true);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid port number");
                }
            }
        });

        joinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = JOptionPane.showInputDialog("Enter host IP:");
                String portStr = JOptionPane.showInputDialog("Enter port number:");
                try {
                    int port = Integer.parseInt(portStr);
                    // Connect to server
                    new MultiPlayerView(false, ip, port).setVisible(true);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid port number");
                }
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainMenuView().setVisible(true);
                dispose();
            }
        });

        add(hostBtn);
        add(joinBtn);
        add(backBtn);
    }
}