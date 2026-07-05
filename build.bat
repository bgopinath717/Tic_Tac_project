@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.11
set PATH=%JAVA_HOME%\bin;%PATH%
echo Building Tic-Tac-Toe jar (skipping tests)...
apache-maven-3.9.8\bin\mvn clean package -DskipTests
