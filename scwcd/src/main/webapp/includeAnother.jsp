<%@ page contentType="text/html; charset=windows-1251"%>
<%@ taglib uri='/WEB-INF/myTag.tld' prefix='myTag'%>
<%@ include file="a.txt"%>
<html>
<head>
<title>includeAnother</title>
</head>
<body bgcolor="#ffffff">
	<h1>include another jsp page</h1>
	This is main page
	<br />
	
	<a href="<%=request.getContextPath()%>/retrievesourcecode"> 
	get source code for this jsp 
	</a>
	<br />
	
	<myTag:firstTag />


	<jsp:include flush="true" page="includedPage.jsp">
		<jsp:param name="paramName" value="paramValue" />
	</jsp:include>

	<br />
	<hr />
	<jsp:include flush="true" page="tag/TagException.jsp" />


</body>
</html>
