<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri='/WEB-INF/myTag.tld' prefix='myTag' %>
<html>
    <head>
        <title>JSTL test--2 </title>
    </head>
    <body>
        <%
            request.setAttribute("ten", "10");
            request.setAttribute("five", new Integer(5));
        %>
        
        ten+five=
        <br/>
        
        <c:out value="${ten+five}"/>
        <br/>
        <c:out value="${'3'*five}"/>
        
        <br/>
        <c:out value="${3.0*five}"/>
        
        <br/>
        <c:out value="${1.5*ten}"/>
        
        <br/>
        <c:out value="${five}+${ten}"/>
        
        <br/>
        <c:catch var='exception'>
            <myTag:tagException/>
        </c:catch>
        <br/>
        <c:out value="${exception.message}"/>
        
        <c:out value="<h2>escapeXML</h2>" escapeXml='false' />
        
        <h2>JSTL can import outside url, the context  will be copy into this page, for example: </h2>
        <c:import url="http://www.google.com"/> 
        
    </body>
</html>
