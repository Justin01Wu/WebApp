<%@ taglib uri='/WEB-INF/myTag.tld' prefix='myTag' %>
<%@ taglib prefix="ex" tagdir="/WEB-INF/tags" %>

<html>
<head>
<title>
simple tag Test
</title>
</head>
<body bgcolor="#ffffff">
<h1>
Simple Tag Test
</h1>

<div style="background-color: #eeeeee;">
<myTag:message/>
<br/><br/>
</div>

<br/><br/>


<div style="background-color: #eeddee;">
Math Functions:<p>
<table border="1">
    <myTag:functions num="${3*2}" pow="2" min="4" max="8">
        This is the body of the simpleTag.
    </myTag:functions>
</table>
<br/><br/>
</div>

The first six numbers in the Fibonacci sequence are:
<ex:example author="Justin"/>
  
  
</body>
</html>
