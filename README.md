# Tic Tac Toe (Spring Boot + Static UI)

This project adds a simple Java (Spring Boot) backend to serve the existing `index.html` UI and provides a small REST API for Tic-Tac-Toe game state.

Prerequisites
- Java 21 (JDK installed)
- Maven 3.9+

Run (development)
1. From the project root run:

   mvn spring-boot:run

2. Open http://localhost:8080/ in your browser. The UI (`index.html`) is served from Spring's `static` resources.

Build jar

   mvn -DskipTests package
   java -jar target/tictactoe-0.0.1-SNAPSHOT.jar

API
- POST /api/game -> creates a new game and returns { id, board, currentPlayer, active }
- GET /api/game/{id} -> returns the game state
- POST /api/game/{id}/move {"position": 0} -> make a move at position 0..8

Notes
- This backend stores game state in memory (Map). It's intended for local development and demo only.

Actuator
- The project now includes Spring Boot Actuator. After starting the app you can check the health endpoint:

   http://localhost:8080/actuator/health

Integration test
- A JUnit integration test was added under `src/test/java` to verify the create→move→load flow. Run it with:

   mvn test

Running locally (quick)
1. Build the jar:

    mvn clean package -DskipTests

2. Run the app (foreground):

    java -jar target/tictactoe-0.0.1-SNAPSHOT.jar

3. Open the UI: http://localhost:8080/

If you make changes to `src/main/resources/static/index.html`, rebuild and restart the app to see updates.
