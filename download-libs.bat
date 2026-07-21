@echo off
echo Downloading Bouncy Castle libraries...
echo.
echo Please download these JAR files manually and place in lib\ folder:
echo.
echo 1. bcprov-jdk15on-1.70.jar
echo    https://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk15on/1.70/bcprov-jdk15on-1.70.jar
echo.
echo 2. bcpkix-jdk15on-1.70.jar
echo    https://repo1.maven.org/maven2/org/bouncycastle/bcpkix-jdk15on/1.70/bcpkix-jdk15on-1.70.jar
echo.
echo After downloading, place them in: %~dp0lib\
echo Then run: run.bat
pause
