@echo off
echo ========================================
echo Running PQC Demo (Kyber + Dilithium)
echo ========================================
echo.

cd /d "%~dp0"

javac -cp "lib\*;src\main\java" src\main\java\com\pqc\postquantum\*.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

java -cp "lib\*;src\main\java" com.pqc.postquantum.PQCDemo

pause
