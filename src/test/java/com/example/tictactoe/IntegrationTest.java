package com.example.tictactoe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Objects;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void createMoveLoadFlow() {
        // create
    ResponseEntity<Map<String, Object>> create = rest.exchange("/api/game", HttpMethod.POST, null,
        new ParameterizedTypeReference<Map<String, Object>>() {
        });
        assertThat(create.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Map<String, Object> body = Objects.requireNonNull(create.getBody(), "create response body");
    assertThat(body).containsKeys("id", "board", "currentPlayer", "active");
    Object idObj = Objects.requireNonNull(body.get("id"), "id");
    String id = idObj.toString();

        // move (position 4)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> moveRequest = new HttpEntity<>("{\"position\":4}", headers);
    ResponseEntity<Map<String, Object>> move = rest.exchange("/api/game/" + id + "/move", HttpMethod.POST, moveRequest,
        new ParameterizedTypeReference<Map<String, Object>>() {
        });
    assertThat(move.getStatusCode()).isEqualTo(HttpStatus.OK);
    Map<String, Object> moveBody = Objects.requireNonNull(move.getBody(), "move response body");
    assertThat(moveBody).containsKey("board");
    Object boardObj = Objects.requireNonNull(moveBody.get("board"), "board");
        assertThat(boardObj).isInstanceOf(java.util.List.class);
        java.util.List<?> boardList = (java.util.List<?>) boardObj;
        assertThat(boardList.get(4)).isEqualTo("X");

        // load
    ResponseEntity<Map<String, Object>> loaded = rest.exchange("/api/game/" + id, HttpMethod.GET, null,
        new ParameterizedTypeReference<Map<String, Object>>() {
        });
        assertThat(loaded.getStatusCode()).isEqualTo(HttpStatus.OK);
    Map<String, Object> loadedBody = Objects.requireNonNull(loaded.getBody(), "loaded response body");
    Object loadedId = Objects.requireNonNull(loadedBody.get("id"), "loaded id");
    assertThat(loadedId.toString()).isEqualTo(id);
    }
}
