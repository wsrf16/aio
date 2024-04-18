@echo off
set version=1.1.10-SNAPSHOT
git push origin --delete tag %version%
git tag -d %version%
git tag -a %version% -m "%version%"
git push origin %version%

