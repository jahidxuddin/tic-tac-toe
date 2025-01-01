package de.ju.tictactoe.service;

import de.ju.tictactoe.gui.GameBoard;
import de.ju.tictactoe.networking.Socket;
import de.ju.tictactoe.shared.GameState;

import java.io.IOException;

public class GameClient {
    private Socket client;
    private String ip;
    private GameBoard gameBoard;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public boolean connectWithOpponent(String ip) {
        this.client = new Socket(ip, 1234);
        return client.connect();
    }

    private void connectWithOpponent() {
        if (client == null) {
            client = new Socket(ip, 1234);
            client.connect();
        }
    }

    public void updateGameState(GameState gameState) {
        connectWithOpponent();
        try {
            client.write("SYNC\n");
            client.write(gameState + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
