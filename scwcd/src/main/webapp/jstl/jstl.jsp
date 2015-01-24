<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import = "testjsp.SimpleBean" %>
<jsp:useBean id="bean0" scope="session" class="testjsp.SimpleBean"/>
<jsp:setProperty name="bean0" property="name" value="justin"/>
<jsp:setProperty name="bean0" property="email" value="wuyg719@gmail.com"/>

<html>
    <head>
        <title>JSTL test</title>
        
    </head>
    
    
    <c:url value="/jstl/jstl.jsp" var="jstl"/>
    <c:out value="${jstl}" />
    <br/>
    
    <body bgcolor="#ffffff">
        
        <c:out value="${8+7}" />
        <br/>
        
        <c:set var="num" value="${4*3}" />
        <c:out value="${num}" />
        <br/>
        
        <c:set var="num2">
            <c:out value="${8+3}" />
        </c:set>
        
        <c:set var="xx" value="${9}" />
        <c:if test="${xx == '9'}">
            xx= <c:out value="${xx}"/>
        </c:if>
        <br/>
        
        <c:forEach var="yy" begin="0" end="9" step="3">
            yy= <c:out value="${yy}"/><br/>
        </c:forEach>
        <br/>
        
        bean0.name: <jsp:getProperty name="bean0" property="name"/>
        <br/>
        bean0.email: <c:out value="${bean0.email}"/>
        <br/>
        <c:set target="${bean0}" property="email" value="justin1"/>
        bean0.email after changed: <c:out value="${bean0.email}"/>
        <br/>
        
        
        <br/>
        <c:set var="numList" value="one,two,three" />
        Output of the forTokens tag:<p>
        <table border="1">
            <c:forTokens var="currentNum" items="${numList}" delims=",">
                <tr>
                    <td>
                        <c:out value="${currentNum}"/>
                    </td>
                </tr>
            </c:forTokens>
        </table>
        
        <c:catch var="e">
            <%int i = 5 / 0;%>
        </c:catch>
        <c:if test="${e != null}">
            Exception occurs.
        </c:if>
        
    </body>
</html>
