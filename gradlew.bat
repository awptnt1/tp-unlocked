@echo off
set DIR=%~dp0
set DIR=%DIR:~0,-1%
"%DIR%\\gradlew" %*
