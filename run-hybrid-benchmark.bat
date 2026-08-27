@echo off
echo ========================================
echo Running Hybrid Benchmark
echo ========================================
echo.

cd /d "%~dp0"

javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java src\main\java\com\pqc\utils\*.java src\main\java\com\pqc\postquantum\*.java src\main\java\com\pqc\hybrid\*.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)

java -cp "lib\*;src\main\java" com.pqc.hybrid.HybridBenchmark

pause
