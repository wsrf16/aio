cd %~dp0
where mvn
echo mvn clean install
call mvn clean install
pause
call install