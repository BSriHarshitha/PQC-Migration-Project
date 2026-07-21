@echo off
echo ========================================
echo RSA CLIENT - Run in Terminal 2
echo ========================================
echo.

cd /d "%~dp0"

echo Compiling client...
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\RSAClientDemo.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Starting client...
echo.
java -cp "lib\*;src\main\java" com.pqc.rsa.RSAClientDemo

pause
