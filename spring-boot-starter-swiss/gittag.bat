@echo off
set version=1.1.5-SNAPSHOT
git push origin --delete tag %version%
git tag -d %version%
git tag -a %version% -m "%version%"
git push origin %version%

