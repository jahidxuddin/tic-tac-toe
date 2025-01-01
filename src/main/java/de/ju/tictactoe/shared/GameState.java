package de.ju.tictactoe.shared;

import java.util.Arrays;

public class GameState {
    private final String[] fieldStates;
    private char clientChar;
    private char playersTurnChar;
    private char playersCharWhoWon;

    public GameState() {
        this.fieldStates = new String[9];
        Arrays.fill(fieldStates, "");
        this.playersTurnChar = 'X';
        this.playersCharWhoWon = '-';
    }

    public String[] getFieldStates() {
        return fieldStates;
    }

    public void updateFieldState(int index, String value) {
        this.fieldStates[index] = value;
    }

    public char getClientChar() {
        return clientChar;
    }

    public void setClientChar(char clientChar) {
        this.clientChar = clientChar;
    }

    public char getPlayersTurnChar() {
        return playersTurnChar;
    }

    public void setPlayersTurnChar(char playersTurnChar) {
        this.playersTurnChar = playersTurnChar;
    }

    public char getPlayersCharWhoWon() {
        return playersCharWhoWon;
    }

    public void setPlayersCharWhoWon(char playersCharWhoWon) {
        this.playersCharWhoWon = playersCharWhoWon;
    }

    @Override
    public String toString() {
        StringBuilder fieldStatesJson = new StringBuilder("[");
        for (int i = 0; i < fieldStates.length; i++) {
            fieldStatesJson.append("\"").append(fieldStates[i]).append("\"");
            if (i < fieldStates.length - 1) {
                fieldStatesJson.append(", ");
            }
        }
        fieldStatesJson.append("]");

        return "{" + "\"clientChar\": \"" + (clientChar == 'X' ? 'O' : 'X') + "\", " + "\"fieldStates\": " + fieldStatesJson + ", " + "\"playersTurnChar\": \"" + playersTurnChar + "\", " + "\"playersCharWhoWon\": \"" + playersCharWhoWon + "\"" + "}";
    }
}
