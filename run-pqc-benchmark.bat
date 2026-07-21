@echo off
echo ========================================
echo Running PQC Benchmark (RSA vs PQC)
echo ========================================
echo.

cd /d "%~dp0"

javac -cp "lib\*;src\main\java" src\main\java\com\pqc\benchmark\PQCBenchmark.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

java -cp "lib\*;src\main\java" com.pqc.benchmark.PQCBenchmark

pause
