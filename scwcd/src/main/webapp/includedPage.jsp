<%@ page contentType="text/html; charset=windows-1251" %>
<html>
<head>
<title>
includedPage
</title>
</head>
<body bgcolor="#ff00ff">
<h1>
This is included page
</h1>
This is passed parameter:<br/>
<%=request.getParameter("paramName")%>
</body>
</html>
