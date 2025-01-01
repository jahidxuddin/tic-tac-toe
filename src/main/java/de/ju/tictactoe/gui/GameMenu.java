package de.ju.tictactoe.gui;

import de.ju.tictactoe.service.GameClient;
import de.ju.tictactoe.shared.GameState;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JFrame {
    private final GameClient gameClient;
    private final GameState gameState;

    public GameMenu(GameClient gameClient, GameState gameState) {
        this.gameClient = gameClient;
        this.gameState = gameState;

        new Thread(() -> {
            while (true) {
                if (gameClient.getGameBoard() != null) {
                    dispose();
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        setTitle("Tic Tac Toe - Menu");
        setSize(400, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel("Enter Opponent IP:");
        JTextField ipField = new JTextField(15);
        JButton connectButton = new JButton("Connect");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(ipField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(connectButton, constraints);

        connectButton.addActionListener(e -> {
            String ipAddress = ipField.getText();
            if (ipAddress != null && !ipAddress.isEmpty()) {
                handleConnection(ipAddress);
            } else {
                JOptionPane.showMessageDialog(GameMenu.this, "Please enter a valid IP address.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void handleConnection(String ip) {
        if (gameClient.connectWithOpponent(ip)) {
            gameState.setClientChar('X');
            gameClient.setGameBoard(new GameBoard(gameClient, gameState));
        } else {
            JOptionPane.showMessageDialog(GameMenu.this, "Failed to connect with opponent", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
