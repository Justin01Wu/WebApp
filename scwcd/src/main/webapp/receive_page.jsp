<%@ page import = "java.util.*, testjsp.*" %>
<html>
    <head>
        <title>
            jsp2
        </title>
    </head>
    
    <jsp:useBean id="bean0" scope="session" class="testjsp.SimpleBean" />
    <jsp:setProperty name="bean0" property="*" />
    
    <body bgcolor="#ffffff">
        <h1>
            This is a display pgae for input data
        </h1>
        <form method="post" action="servlet1">
            <br><br>
            <input type="submit" name="Submit" value="Submit">
            <input type="reset" value="Reset">
            <hr>bean0<br>
            <br>email <jsp:getProperty name="bean0" property="email"/>
            <br>name <jsp:getProperty name="bean0" property="name"/>
            <br>
            
        </form>
    </body>
</html>
