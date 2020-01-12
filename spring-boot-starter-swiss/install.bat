@echo off
echo %cd%
cd %~dp0
where mvn
echo mvn clean install
call mvn clean install -Dspring-boot.repackage.skip=true
pause
call install