@echo off
echo ========================================
echo PQC Migration Project - Setup Verification
echo ========================================
echo.

cd /d "%~dp0"

echo [1/4] Checking Java installation...
java -version 2>nul
if errorlevel 1 (
    echo ERROR: Java not found! Please install Java 11 or higher.
    pause
    exit /b 1
)
echo OK - Java is installed
echo.

echo [2/4] Checking project structure...
if not exist "lib\bcprov-jdk15on-1.70.jar" (
    echo ERROR: Bouncy Castle library not found in lib\
    pause
    exit /b 1
)
if not exist "src\main\java\com\pqc\rsa\RSACrypto.java" (
    echo ERROR: RSA source files not found
    pause
    exit /b 1
)
if not exist "src\main\java\com\pqc\postquantum\KyberCrypto.java" (
    echo ERROR: PQC source files not found
    pause
    exit /b 1
)
echo OK - All files present
echo.

echo [3/4] Compiling RSA components...
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\rsa\*.java 2>nul
if errorlevel 1 (
    echo ERROR: RSA compilation failed
    pause
    exit /b 1
)
echo OK - RSA compiled
echo.

echo [4/4] Compiling PQC components...
javac -cp "lib\*;src\main\java" src\main\java\com\pqc\postquantum\*.java 2>nul
if errorlevel 1 (
    echo ERROR: PQC compilation failed
    pause
    exit /b 1
)
echo OK - PQC compiled
echo.

echo ========================================
echo SUCCESS! Project is ready to run
echo ========================================
echo.
echo Available demos:
echo   1. run-demo.bat           - RSA encryption demo
echo   2. run-custom-attack.bat  - Interactive attack demo
echo   3. run-benchmark.bat      - RSA performance test
echo   4. run-pqc-demo.bat       - PQC interactive demo
echo   5. run-pqc-benchmark.bat  - PQC performance test
echo   6. run-comparison.bat     - Side-by-side comparison
echo.
echo Recommended first run: run-comparison.bat
echo.
pause
