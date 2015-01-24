<%@page session="true" autoFlush="true"%>
<%@ page import="testjsp.SimpleBean"%>
<html>
<head>
<title>jsp1</title>
<script type="text/javascript">
	function tojsp2() {
		document.forms[0].action = "receive_page.jsp"
		document.forms[0].submit();
	}
	function toServlet() {
		document.forms[0].action = "controlservlet"
		document.forms[0].submit();
	}
</script>
</head>

<body bgcolor="#ffffff">
	<h1>test java bean</h1>

	<form method="post" action="forward.jsp">
		<jsp:useBean id="bean0" scope="session" class="testjsp.SimpleBean" />
		<jsp:setProperty name="bean0" property="*" />
		<br> <br> <input type="submit" name="Submit"
			value="Submit to forward.jsp"> <input type="button"
			name="anothersubmit" value="submit to jsp"
			onclick="javascript:tojsp2();"> <input type="button"
			name="anothersubmit" value="submit to servlet"
			onclick="javascript:toServlet();">

		<hr>
		bean name: bean0 <br>
		<%
			if (bean0.getErrMsg() != null) {
		%>
		<font color="red"> <jsp:getProperty name="bean0"
				property="errMsg" /> <br>
		</font>
		<%
			}
		%>

		subject <input readonly="readonly" name="subject" maxLength="20"
			value="Application from our web site" style="width: 300px"> <br />
		Enter new value for bean0.name : <input name="name" maxLength="20"
			value="<%=bean0 != null ? bean0.getName() : ""%>"> <br />
		Enter new value for bean0.email : <input name="email"
			value="<%=bean0 != null ? bean0.getEmail() : ""%>"> <br />
		<input type="checkbox" name="interests" value="Engineer">Engineer

		<br>
		<input type="checkbox" name="interests" value="Senior">Senior
		<br />
		<hr />
		<br /> <br /> <a
			href="<%=request.getContextPath()%>/retrievesourcecode">get
			source code for this jsp</a>
	</form>

</body>
</html>
