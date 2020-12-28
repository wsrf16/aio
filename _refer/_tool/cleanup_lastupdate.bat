::set USERPROFILE=C:\Users\PPC
set REPOSITORY_PATH=%USERPROFILE%\.m2\repository
rem 正在搜索...
for /f "delims=" %%i in ('dir /b /s "%REPOSITORY_PATH%\*lastUpdated*"') do (
    rem dir /s /q %%i
    del /s /q %%i
)
rem 搜索完毕
pause




