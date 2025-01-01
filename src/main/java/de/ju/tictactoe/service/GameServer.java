package de.ju.tictactoe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ju.tictactoe.gui.GameBoard;
import de.ju.tictactoe.networking.ServerSocket;
import de.ju.tictactoe.networking.Socket;
import de.ju.tictactoe.shared.GameState;

import javax.swing.*;
import java.io.IOException;

public class GameServer {
    private final Socket client;
    private GameState gameState;
    private final GameClient gameClient;

    public GameServer(int port, GameState gameState, GameClient gameClient) throws IOException {
        this.gameState = gameState;
        this.gameClient = gameClient;
        ServerSocket serverSocket = new ServerSocket(port);
        this.client = serverSocket.accept();
        gameClient.setIp(client.getHostname());
        listenForGameStateSync();
    }

    private void listenForGameStateSync() {
        boolean isInitSync = true;
        while (true) {
            try {
                if (client.readLine().equals("SYNC")) {
                    String json = client.readLine();
                    gameState = new ObjectMapper().readValue(json, GameState.class);

                    if (gameState.getPlayersCharWhoWon() != '-') {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, gameState.getPlayersCharWhoWon() + " has won.", "Game End", JOptionPane.PLAIN_MESSAGE));
                        gameClient.getGameBoard().dispose();
                    }

                    if (isInitSync && gameClient.getGameBoard() == null) {
                        SwingUtilities.invokeLater(() -> gameClient.setGameBoard(new GameBoard(gameClient, gameState)));
                        isInitSync = false;
                    }

                    if (gameClient.getGameBoard() != null) {
                        SwingUtilities.invokeLater(() -> gameClient.getGameBoard().updateGameBoard(gameState));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
