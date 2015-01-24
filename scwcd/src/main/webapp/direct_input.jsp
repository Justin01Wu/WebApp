<%@page session="true" autoFlush="true"%>
<%@page import="java.util.*, testjsp.*"%>
<html>
<head>
<title>jsp1</title>
<script type="text/javascript">
	function tojsp2() {
		document.forms[0].action = "receive_page.jsp"
		document.forms[0].submit();
	}
</script>
</head>
<%
	SimpleBean bean0 = (SimpleBean) request.getAttribute("bean0");
%>
<body bgcolor="#ffffff">
	<h1>JBuilder Generated JSP</h1>
	<form method="post" action="forward.jsp">
		<br> <br> <input type="submit" name="Submit"
			value="Submit to Servlet"> <input type="button"
			name="anothersubmit" value="submit to jsp"
			onclick="javascript:tojsp2();"> <input type="reset"
			value="Reset">
		<hr>
		bean0 <br>
		<%
			if (null != bean0 && bean0.getErrMsg() != null) {
		%>
		<font color="red"> <jsp:getProperty name="bean0"
				property="errMsg" /> <br>
		</font>
		<%
			}
		%>
		subject <input readonly="readonly" name="subject" maxLength="20"
			value="Application from our web site"> <br> Enter new
		value for bean0.name : <input name="name" maxLength="20"
			value="<%=bean0 != null ? bean0.getName() : ""%>"> <br> Enter
		new value for bean0.email : <input name="email"
			value="<%=bean0 != null ? bean0.getEmail() : ""%>"> <br> <input
			type="checkbox" name="interests"
			value="Power Systems Design/Automation Engineer"> Power
		Systems Design/Automation Engineer <br> <input type="checkbox"
			name="interests" value="Senior Wireless Hardware Engineer">
		Senior Wireless Hardware Engineer <br> <br>
		<hr>
		<br> <br>
	</form>
</body>
</html>
