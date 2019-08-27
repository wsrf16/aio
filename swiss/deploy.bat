@echo off
cd %~dp0
echo %cd%
where mvn
echo mvn clean deploy
call mvn clean deploy
pause
call deploy
