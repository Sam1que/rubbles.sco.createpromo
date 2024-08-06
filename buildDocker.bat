@echo off
set CURRENT_DIR=%~dp0
set REGISTRY=10.250.182.8:5000
cd /d %CURRENT_DIR%
for %%i in ("%CURRENT_DIR:~0,-1%") do (
    set "IMAGE_NAME=%%~nxi"
)
echo Start build docker image: %IMAGE_NAME%
docker build -t %REGISTRY%/%IMAGE_NAME%:latest .

if %ERRORLEVEL% EQU 0 (
    docker push %REGISTRY%/%IMAGE_NAME%:latest
) else (
    echo Ошибка: Сборка Docker образа завершилась неудачно.
)