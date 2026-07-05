package com.example.tictactoe.service;

import com.example.tictactoe.model.Game;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final Map<UUID, Game> games = new HashMap<>();

    public Game createGame() {
        return createGame(null);
    }

    public Game createGame(String player1Name) {
        Game g = new Game();
        if (player1Name != null && !player1Name.trim().isEmpty()) {
            g.setPlayer1Name(player1Name.trim());
        }
        games.put(g.getId(), g);
        return g;
    }

    public Optional<Game> getGame(UUID id) {
        return Optional.ofNullable(games.get(id));
    }

    public Optional<Game> joinGame(UUID id, String player2Name) {
        Game g = games.get(id);
        if (g == null) return Optional.empty();
        synchronized (g) {
            if (player2Name != null && !player2Name.trim().isEmpty()) {
                g.setPlayer2Name(player2Name.trim());
            }
        }
        return Optional.of(g);
    }

    public Optional<Game> resetGame(UUID id) {
        Game g = games.get(id);
        if (g == null) return Optional.empty();
        synchronized (g) {
            Arrays.fill(g.getBoard(), null);
            g.setCurrentPlayer("X");
            g.setActive(true);
            g.setWinningLine(null);
        }
        return Optional.of(g);
    }

    public Optional<Game> timeout(UUID id, String expectedPlayer) {
        Game g = games.get(id);
        if (g == null) return Optional.empty();
        synchronized (g) {
            if (g.isActive() && g.getCurrentPlayer().equals(expectedPlayer)) {
                g.switchPlayer();
            }
        }
        return Optional.of(g);
    }

    public Optional<Game> makeMove(UUID id, int pos) {
        Game g = games.get(id);
        if (g == null) return Optional.empty();
        synchronized (g) {
            boolean ok = g.makeMove(pos);
            if (!ok) return Optional.of(g);

            // check winner
            var result = checkWinner(g.getBoard());
            if (result != null) {
                g.setActive(false);
                g.setWinningLine(result);
                    } else if (Arrays.stream(g.getBoard()).noneMatch(s -> s == null)) {
                g.setActive(false);
            } else {
                g.switchPlayer();
            }
        }
        return Optional.of(g);
    }

    private List<Integer> checkWinner(String[] board) {
        int[][] combos = new int[][]{ {0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6} };
        for (int[] c : combos) {
                if (board[c[0]] != null && board[c[0]].equals(board[c[1]]) && board[c[0]].equals(board[c[2]])) {
                return Arrays.asList(c[0], c[1], c[2]);
            }
        }
        return null;
    }
}
