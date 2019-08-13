@echo off
cd %~dp0
echo %cd%
where mvn
call mvn clean deploy
pause
call deploy
