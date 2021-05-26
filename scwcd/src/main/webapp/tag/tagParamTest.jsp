<%@ taglib uri='/WEB-INF/myTag.tld' prefix='myTag'%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
<head>
<title>tagTest</title>
</head>
<body bgcolor="#ffffff">
	<h1>Tag param Test</h1>


	<myTag:tagParam age="45years"/>		
	
	<br />
	
	<c:set var="myPeople" value='${["Justin Wu", "Rita Wang"]}' scope="request" />
	<myTag:tagListParam people="${myPeople}"/>		

</body>
</html>
