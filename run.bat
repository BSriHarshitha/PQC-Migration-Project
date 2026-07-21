@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-25.0.2
set PATH=%JAVA_HOME%\bin;%PATH%

echo Compiling Java files...
javac -d target\classes -cp "lib\*" src\main\java\com\pqc\*.java src\main\java\com\pqc\utils\*.java src\main\java\com\pqc\rsa\*.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Running RSA Demo...
java -cp "target\classes;lib\*" com.pqc.rsa.RSADemo

pause
