@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.11
set PATH=%JAVA_HOME%\bin;%PATH%
echo Starting Tic-Tac-Toe Spring Boot Application...
apache-maven-3.9.8\bin\mvn spring-boot:run
