package view;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenuView mainMenu = new MainMenuView();
            mainMenu.setVisible(true);
        });
    }
}