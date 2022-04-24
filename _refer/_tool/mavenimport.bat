@echo off
setlocal EnableDelayedExpansion
if "%1"=="" (
  echo �������ϴ�url
  goto end
)

dir/b/s>_111.txt
set pwd=%~dp0
set REPO_URL=%1
set USERNAME=%2
set PASSWORD=%3

if "%pwd:~-1%" == "\" set pwdd=%pwd:~0,-1%
if "%REPO_URL:~-1%" == "/" set REPO_URL=%REPO_URL:~0,-1%

for /f "delims=" %%a in (_111.txt) do (
  set "b=%%a"
  set "b=!b:%pwd%=!"
  set "b=!b:%pwdd%=!"
  set "b=!b:\=/!"
  set "relative=!b!"
  set "full=%REPO_URL%/%relative%"
  echo 'curl -u "%USERNAME%:%PASSWORD%" -X PUT -v -T !relative! !full!'
  echo from: !relative!
  echo   to: !full!
  curl -u "%USERNAME%:%PASSWORD%" -X PUT -v -T !relative! !full!
  echo ------------------
  echo ""
  
)

:end


rem curl -u "$USERNAME:$PASSWORD" -X PUT -v -T {} ${REPO_URL}/{}
rem setlocal disabledelayedexpansion   
rem setlocal EnableDelayedExpansion
rem curl -u "$USERNAME:$PASSWORD" -X PUT -v -T {} ${REPO_URL}/{}
rem curl -X PUT -v -T !b! ${REPO_URL}/!b!
rem endlocal
rem echo cmd /v:off
rem set "b=1"
rem echo %b%
rem http://www.baidu.com
rem mavenimport http://abc.com/artifactory/zhaoy-test-maven-releases-local/



