@echo off
echo %cd%
cd %~dp0
where mvn
echo mvn clean deploy
call mvn clean deploy -Dspring-boot.repackage.skip=true
pause
call deploy
