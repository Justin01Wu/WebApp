<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

    <h1>JSP Page</h1>
    
    The following java script can't be compiled when scripting-invalid is set to true in jsp-config of web.xml:<br/>
    
    <% int i = 0;
for(; i< 10; i++) { 
    out.println(i); 
} %>

<br/>
    The following EI is regared as html code when el-ignored is set to true in jsp-config of web.xml:<br/>
     pageContext.servletConfig.servletName = ${pageContext.servletConfig.servletName}<br/>  
    </body>
</html>
