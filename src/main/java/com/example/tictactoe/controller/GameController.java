package com.example.tictactoe.controller;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<?> createGame(@RequestBody(required = false) Map<String, Object> body) {
        String player1Name = null;
        if (body != null && body.containsKey("player1Name")) {
            player1Name = (String) body.get("player1Name");
        }
        Game g = gameService.createGame(player1Name);
        Map<String,Object> resp = new HashMap<>();
        resp.put("id", g.getId());
        resp.put("board", g.getBoard());
        resp.put("currentPlayer", g.getCurrentPlayer());
        resp.put("active", g.isActive());
        resp.put("player1Name", g.getPlayer1Name());
        resp.put("player2Name", g.getPlayer2Name());
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGame(@PathVariable String id) {
        UUID uuid;
        try { uuid = UUID.fromString(id); } catch (Exception e) { return ResponseEntity.badRequest().body("Invalid id"); }
        Optional<Game> g = gameService.getGame(uuid);
        if (g.isPresent()) {
            Game game = g.get();
            Map<String,Object> resp = new HashMap<>();
            resp.put("id", game.getId());
            resp.put("board", game.getBoard());
            resp.put("currentPlayer", game.getCurrentPlayer());
            resp.put("active", game.isActive());
            resp.put("player1Name", game.getPlayer1Name());
            resp.put("player2Name", game.getPlayer2Name());
            resp.put("winningLine", game.getWinningLine());
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        }
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<?> joinGame(@PathVariable String id, @RequestBody Map<String, Object> body) {
        UUID uuid;
        try { uuid = UUID.fromString(id); } catch (Exception e) { return ResponseEntity.badRequest().body("Invalid id"); }
        
        String player2Name = (String) body.get("player2Name");
        if (player2Name == null || player2Name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("player2Name is required");
        }
        
        Optional<Game> g = gameService.joinGame(uuid, player2Name);
        if (g.isPresent()) {
            Game game = g.get();
            Map<String,Object> resp = new HashMap<>();
            resp.put("id", game.getId());
            resp.put("board", game.getBoard());
            resp.put("currentPlayer", game.getCurrentPlayer());
            resp.put("active", game.isActive());
            resp.put("player1Name", game.getPlayer1Name());
            resp.put("player2Name", game.getPlayer2Name());
            resp.put("winningLine", game.getWinningLine());
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        }
    }

    @PostMapping("/{id}/reset")
    public ResponseEntity<?> resetGame(@PathVariable String id) {
        UUID uuid;
        try { uuid = UUID.fromString(id); } catch (Exception e) { return ResponseEntity.badRequest().body("Invalid id"); }
        
        Optional<Game> g = gameService.resetGame(uuid);
        if (g.isPresent()) {
            Game game = g.get();
            Map<String,Object> resp = new HashMap<>();
            resp.put("id", game.getId());
            resp.put("board", game.getBoard());
            resp.put("currentPlayer", game.getCurrentPlayer());
            resp.put("active", game.isActive());
            resp.put("player1Name", game.getPlayer1Name());
            resp.put("player2Name", game.getPlayer2Name());
            resp.put("winningLine", game.getWinningLine());
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        }
    }

    @PostMapping("/{id}/timeout")
    public ResponseEntity<?> timeout(@PathVariable String id, @RequestBody(required = false) Map<String, Object> body) {
        UUID uuid;
        try { uuid = UUID.fromString(id); } catch (Exception e) { return ResponseEntity.badRequest().body("Invalid id"); }
        
        String expectedPlayer = null;
        if (body != null && body.containsKey("currentPlayer")) {
            expectedPlayer = (String) body.get("currentPlayer");
        }
        
        Optional<Game> g = gameService.timeout(uuid, expectedPlayer);
        if (g.isPresent()) {
            Game game = g.get();
            Map<String,Object> resp = new HashMap<>();
            resp.put("id", game.getId());
            resp.put("board", game.getBoard());
            resp.put("currentPlayer", game.getCurrentPlayer());
            resp.put("active", game.isActive());
            resp.put("player1Name", game.getPlayer1Name());
            resp.put("player2Name", game.getPlayer2Name());
            resp.put("winningLine", game.getWinningLine());
            return ResponseEntity.ok(resp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        }
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<?> makeMove(@PathVariable String id, @RequestBody Map<String, Object> body) {
        UUID uuid;
        try { uuid = UUID.fromString(id); } catch (Exception e) { return ResponseEntity.badRequest().body("Invalid id"); }

        Object posObj = body.get("position");
        if (posObj == null) return ResponseEntity.badRequest().body("position is required");
        int pos;
        try { pos = (int) ((Number) posObj).intValue(); } catch (Exception e) { return ResponseEntity.badRequest().body("position must be a number 0-8"); }

        Optional<Game> g = gameService.makeMove(uuid, pos);
        if (g.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
        Game game = g.get();
        Map<String,Object> resp = new HashMap<>();
        resp.put("id", game.getId());
        resp.put("board", game.getBoard());
        resp.put("currentPlayer", game.getCurrentPlayer());
        resp.put("active", game.isActive());
        resp.put("player1Name", game.getPlayer1Name());
        resp.put("player2Name", game.getPlayer2Name());
        resp.put("winningLine", game.getWinningLine());
        return ResponseEntity.ok(resp);
    }
}
