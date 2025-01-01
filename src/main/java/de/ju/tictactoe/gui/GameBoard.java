package de.ju.tictactoe.gui;

import de.ju.tictactoe.service.GameClient;
import de.ju.tictactoe.shared.GameState;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JFrame {
    private final GameClient gameClient;
    private GameState gameState;

    public GameBoard(GameClient gameClient, GameState gameState) {
        this.gameClient = gameClient;
        this.gameState = gameState;
        if (gameState.getClientChar() != 'X') {
            gameState.setClientChar('O');
        }
        gameClient.updateGameState(gameState);

        setTitle("Tic Tac Toe");
        setSize(600, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        String[] fieldStates = gameState.getFieldStates();
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 10, 10));
        boardPanel.setBackground(Color.BLACK);

        for (int i = 0; i < fieldStates.length; i++) {
            JButton button = new JButton();
            button.setText(fieldStates[i]);
            button.setFont(new Font("Arial", Font.BOLD, 60));
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.DARK_GRAY);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            int index = i;
            button.addActionListener(e -> handleButtonClick(index, button));
            boardPanel.add(button);
        }

        add(boardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void handleButtonClick(int index, JButton button) {
        if (gameState.getPlayersTurnChar() != gameState.getClientChar()) {
            return;
        }
        if (!button.getText().isEmpty()) {
            return;
        }
        button.setText(String.valueOf(gameState.getClientChar()));
        gameState.updateFieldState(index, String.valueOf(gameState.getClientChar()));
        if (checkBoard()) {
            gameState.setPlayersCharWhoWon(gameState.getPlayersTurnChar());
            gameClient.updateGameState(gameState);
            JOptionPane.showMessageDialog(GameBoard.this, gameState.getPlayersCharWhoWon() + " has won.", "Game End", JOptionPane.PLAIN_MESSAGE);
            dispose();
            System.exit(0);
        } else {
            gameState.setPlayersTurnChar(gameState.getPlayersTurnChar() == 'X' ? 'O' : 'X');
        }
        gameClient.updateGameState(gameState);
    }

    private boolean checkBoard() {
        String[] fieldStates = gameState.getFieldStates();

        for (int i = 0; i < 3; i++) {
            if (!fieldStates[i * 3].isEmpty() && fieldStates[i * 3].equals(fieldStates[i * 3 + 1]) && fieldStates[i * 3].equals(fieldStates[i * 3 + 2])) {
                gameState.setPlayersCharWhoWon(gameState.getPlayersTurnChar());
                return true;
            }
            if (!fieldStates[i].isEmpty() && fieldStates[i].equals(fieldStates[i + 3]) && fieldStates[i].equals(fieldStates[i + 6])) {
                gameState.setPlayersCharWhoWon(gameState.getPlayersTurnChar());
                return true;
            }
        }

        if (!fieldStates[0].isEmpty() && fieldStates[0].equals(fieldStates[4]) && fieldStates[0].equals(fieldStates[8])) {
            gameState.setPlayersCharWhoWon(gameState.getPlayersTurnChar());
            return true;
        }
        if (!fieldStates[2].isEmpty() && fieldStates[2].equals(fieldStates[4]) && fieldStates[2].equals(fieldStates[6])) {
            gameState.setPlayersCharWhoWon(gameState.getPlayersTurnChar());
            return true;
        }

        return false;
    }

    public void updateGameBoard(GameState gameState) {
        this.gameState = gameState;

        JPanel boardPanel = (JPanel) getContentPane().getComponent(0);

        String[] fieldStates = gameState.getFieldStates();
        for (int i = 0; i < fieldStates.length; i++) {
            JButton button = (JButton) boardPanel.getComponent(i);
            button.setText(fieldStates[i]);
        }

        revalidate();
        repaint();
    }
}
