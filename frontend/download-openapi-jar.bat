@echo off
echo Starting download script in GitBash...

REM change to directory of this .bat-file
cd /d "%~dp0"

REM execute the shell script inside the GitBash
"C:\Program Files\Git\git-bash.exe" download-openapi-jar.sh
