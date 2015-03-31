call mvn clean package

rem set tomcat home
rem SET CATALINA_HOME=C:\program2\tomcat-7.0.55-x64
set CATALINA_HOME=C:\program2\apache-tomcat-7.0.55
set TARGET_NAME=jersey-json

rem shutdown tomcat
call %CATALINA_HOME%\bin\shutdown.bat

rem wait for seconds
ping 1.1.1.1 -n 1 -w 3000 > nul

rem delete deploy target folder
echo.
echo deleting folder %CATALINA_HOME%\webapps\%TARGET_NAME%
rmdir /S /Q %CATALINA_HOME%\webapps\%TARGET_NAME%
echo deleting folder %CATALINA_HOME%\work\Catalina
rmdir /S /Q %CATALINA_HOME%\work\Catalina

rem copy war file
echo copying file target\%TARGET_NAME%.war
copy /Y target\%TARGET_NAME%.war %CATALINA_HOME%\webapps

rem start tomcat
set startdir=%cd%
echo current folder is %startdir%
cd %CATALINA_HOME%\bin
call _debug.bat
cd %startdir%

rem pause