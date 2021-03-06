call mvn clean package
rem call mvn clean package

rem shutdown tomcat
SET CATALINA_HOME=C:\program2\apache-tomcat-7.0.55
call C:\program2\apache-tomcat-7.0.55\bin\shutdown.bat

rem wait for seconds
ping 1.1.1.1 -n 1 -w 3000 > nul

rem delete deploy target folder
echo.
echo deleting folder C:\program2\apache-tomcat-7.0.55\webapps\restful
rmdir /S /Q C:\program2\apache-tomcat-7.0.55\webapps\restful
echo deleting folder C:\program2\apache-tomcat-7.0.55\work\Catalina
rmdir /S /Q C:\program2\apache-tomcat-7.0.55\work\Catalina

rem copy war file
echo copying file target\restful.war
copy /Y target\restful.war C:\program2\apache-tomcat-7.0.55\webapps

rem start tomcat
cd C:\program2\apache-tomcat-7.0.55\bin
call _debug.bat
cd C:\projects\WebApp\WebApp\spring3\restful

rem pause