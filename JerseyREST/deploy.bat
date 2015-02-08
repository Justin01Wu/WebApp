call mvn clean package
rem call mvn clean package

rem shutdown tomcat
SET CATALINA_HOME=C:\program2\tomcat-7.0.55-x64
call C:\program2\tomcat-7.0.55-x64\bin\shutdown.bat

rem wait for seconds
ping 1.1.1.1 -n 1 -w 3000 > nul

rem delete deploy target folder
echo.
echo deleting folder C:\program2\tomcat-7.0.55-x64\webapps\jersey-rest
rmdir /S /Q C:\program2\tomcat-7.0.55-x64\webapps\jersey-rest
echo deleting folder C:\program2\tomcat-7.0.55-x64\work\Catalina
rmdir /S /Q C:\program2\tomcat-7.0.55-x64\work\Catalina

rem copy war file
echo copying file target\jersey-rest.war
copy /Y target\jersey-rest.war C:\program2\tomcat-7.0.55-x64\webapps

rem start tomcat
cd C:\program2\tomcat-7.0.55-x64\bin
call _debug.bat
cd C:\projects\WebApp\WebApp\JerseyREST

rem pause