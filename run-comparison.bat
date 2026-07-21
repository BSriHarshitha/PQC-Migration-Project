@echo off
echo ========================================
echo RSA vs PQC Comparison Demo
echo ========================================
echo.

cd /d "%~dp0"

javac -cp "lib\*;src\main\java" src\main\java\com\pqc\postquantum\RSAvsPQC.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

java -cp "lib\*;src\main\java" com.pqc.postquantum.RSAvsPQC

pause
