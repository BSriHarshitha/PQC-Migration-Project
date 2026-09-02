@echo off
echo Compiling PQC Web Demo...
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java src\main\java\com\pqc\utils\*.java src\main\java\com\pqc\postquantum\*.java src\main\java\com\pqc\hybrid\*.java src\main\java\com\pqc\web\CryptoServer.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo.
echo Starting PQC Web Demo Server...
echo Open your browser at: http://localhost:8080
echo Press Ctrl+C to stop the server
echo.
java -cp "lib\*;src\main\java" com.pqc.web.CryptoServer
pause
