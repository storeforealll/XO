package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import viewmodel.SinglePlayerViewModel;

public class MainMenuView extends JFrame {
    public MainMenuView() {
        setTitle("Tic Tac Toe - Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton singlePlayerBtn = new JButton("Single Player");
        JButton multiPlayerBtn = new JButton("Multiplayer");
        JButton exitBtn = new JButton("Exit");

        singlePlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SinglePlayerView(new SinglePlayerViewModel()).setVisible(true);
                dispose();
            }
        });

        multiPlayerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NetworkConfigView().setVisible(true);
                dispose();
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(singlePlayerBtn);
        add(multiPlayerBtn);
        add(exitBtn);
    }
}