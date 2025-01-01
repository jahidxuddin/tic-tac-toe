package de.ju.tictactoe;

import de.ju.tictactoe.gui.GameMenu;
import de.ju.tictactoe.service.GameClient;
import de.ju.tictactoe.service.GameServer;
import de.ju.tictactoe.shared.GameState;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameClient gameClient = new GameClient();
        GameState gameState = new GameState();
        SwingUtilities.invokeLater(() -> new GameMenu(gameClient, gameState));
        new GameServer(1234, gameState, gameClient);
    }
}
