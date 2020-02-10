<%@page session="true" autoFlush="true"%>
<%@ page import="testjsp.SimpleBean"%>
<html>
<head>
<title>myThreads</title>
</head>

<body bgcolor="#ffffff">
	<h1>display all running threads</h1>
	
	<b>Running Threads</b>
<%  
String result = scwcd.MyThread.show();
%>
<pre><%= result %></pre>


</body>
</html>
