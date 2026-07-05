package com.example.tictactoe.model;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Game {
    private final UUID id = UUID.randomUUID();
    private final String[] board = new String[9];
    private String currentPlayer = "X";
    private boolean active = true;
    private String player1Name = "Player 1";
    private String player2Name;
    private List<Integer> winningLine;

    public Game() {
           Arrays.fill(board, null);
    }

    public UUID getId() { return id; }

    public String[] getBoard() { return board; }

    public String getCurrentPlayer() { return currentPlayer; }

    public void setCurrentPlayer(String currentPlayer) { this.currentPlayer = currentPlayer; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public String getPlayer1Name() { return player1Name; }

    public void setPlayer1Name(String player1Name) { this.player1Name = player1Name; }

    public String getPlayer2Name() { return player2Name; }

    public void setPlayer2Name(String player2Name) { this.player2Name = player2Name; }

    public List<Integer> getWinningLine() { return winningLine; }

    public void setWinningLine(List<Integer> winningLine) { this.winningLine = winningLine; }

    public synchronized boolean makeMove(int pos) {
        if (!active) return false;
        if (pos < 0 || pos > 8) return false;
            if (board[pos] != null) return false;
        board[pos] = currentPlayer;
        return true;
    }

    public void switchPlayer() {
        this.currentPlayer = "X".equals(this.currentPlayer) ? "O" : "X";
    }

    public void setBoard(String[] newBoard) {
        System.arraycopy(newBoard, 0, board, 0, Math.min(newBoard.length, board.length));
    }
}
