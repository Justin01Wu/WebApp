@echo off

rem call maven to build
call mvn clean package

SET CATALINA_HOME=C:\program2\apache-tomcat-5.5.26

rem shutdown tomcat
call %CATALINA_HOME%\bin\shutdown.bat

rem wait for seconds
ping 1.1.1.1 -n 1 -w 3000 > nul

rem delete deploy target folder
echo.
echo deleting folder %CATALINA_HOME%\webapps\jersey-rest
rmdir /S /Q %CATALINA_HOME%\webapps\jersey-rest
echo deleting folder %CATALINA_HOME%\work\Catalina
rmdir /S /Q %CATALINA_HOME%\work\Catalina

rem copy war file
echo copying file target\jersey-rest.war
copy /Y target\jersey-rest.war %CATALINA_HOME%\webapps

rem start tomcat
set startdir=%cd%
cd %CATALINA_HOME%\bin
call _debug.bat
cd %startdir%
