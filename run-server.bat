@echo off
echo ========================================
echo RSA SERVER - Run in Terminal 1
echo ========================================
echo.

cd /d "%~dp0"

echo Compiling server...
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\RSAServerDemo.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo.
echo Starting server...
echo.
java -cp "lib\*;src\main\java" com.pqc.rsa.RSAServerDemo

pause
