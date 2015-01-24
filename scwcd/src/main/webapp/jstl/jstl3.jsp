<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="testjsp.SimpleBean" %>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <h1>JSTL Test Page 3 </h1>
        
        <c:url var="home" value='/index.html'>
            <c:param name="p" value="dd<d>"/>
        </c:url>
        <a href="<c:out value='${home}'/>">Home Page with a special parameter</a>
        <br/>
        
        <%
            Map<String, String> numberMap = new HashMap<String, String>();
            numberMap.put("key_1", "value_1");
            numberMap.put("key_2", "value_2");
            numberMap.put("key_3", "value_3");
            request.setAttribute("numberMap", numberMap);

        %>
        
        c:forEach can iterate over collections as well as maps:<br/>    
        <c:forEach var="p" items="${numberMap}" begin="0" end="5"> 
            <c:out value="${p.key}"/> = <c:out value="${p.value}"/><br> 
        </c:forEach>
        
        <br/><br/>
        
        <%
            ArrayList<SimpleBean> list = new ArrayList<SimpleBean>();
            SimpleBean bean = new SimpleBean();
            bean.setName("aaa");
            list.add(bean);

            bean = new SimpleBean();
            bean.setName("bbb");
            list.add(bean);

            request.setAttribute("list", list);

        %>
        
        
        The EL code within the body of the tag may refer to the iteration variable <br/>
        but scripting code cannot:<br/>
        <c:forEach var="bean" items="${list}"> 
            <c:out value="${bean.name}"/><br><%=bean.getName()%> 
        </c:forEach>
        
        <br/><br/>
        The status object can be used while looping over integers using begin and end attributes.<br/>
        <c:forEach var="i" begin="10" end="30" step="5" varStatus="status"> 
            <c:if test="${status.first}"> 
                begin:<c:out value="${status.begin}"/>  end:<c:out value="${status.end}"/>   step:<c:out value="${status.step}"/> 
                <br/>
            </c:if> 
            <c:out value="${i}"/><br>
        </c:forEach>
        
        <br/><br/>
        <c:forEach var="tok" items="a,b,c">
            <c:out value="${tok}"/><br>
        </c:forEach>
        
        
    </body>
</html>
