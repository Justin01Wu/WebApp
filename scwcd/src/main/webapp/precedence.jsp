<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

    <h1>JSP Page</h1>
    
<% int[] ia = { 10, 110, 1110 }; request.setAttribute("ia", ia); %>

${6 + 9 ne 9}

${ requestScope.ia[1] ne 10 + 100 div 0}
${requestScope.ia[2] div 0 }

${ Integer.Infinity eq 10 + 100 div 0}
${ 'Infinity' eq 10 + 100 div 0}
    
    </body>
</html>
