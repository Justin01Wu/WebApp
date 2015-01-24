<jsp:useBean id="bean0" scope="session" class="testjsp.SimpleBean" />
<jsp:setProperty name="bean0" property="*" />
<%
//SimpleBean bean0=(SimpleBean)session.getAttribute("bean0");
//System.out.println("bean0.name="+bean0.getName());
//session.setAttribute("bean0",bean0);

// Because servlet can't get a bean directly from a jsp input page
// So I set the jsp forward to pass the bean to
// the control servlet
String toPage;
if(bean0==null) System.out.println("bean0 is null");
if(bean0.getName()==null) {
  bean0.setErrMsg("Name can not be null");
  toPage="bean_input.jsp";  //request.getRequestURI(); this get jsp3.jsp}
}
else{
  if(bean0.getName().equals("")){
    bean0.setErrMsg("Name can not be null");
    toPage="bean_input.jsp";
  }
  else
    toPage="controlservlet";
}

response.sendRedirect(toPage);
//<jsp:forward  page="<%=toPage%>" />
%>




