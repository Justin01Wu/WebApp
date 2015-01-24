<html>
    <head>
        <title>
            initTest
        </title>
    </head>
    <body bgcolor="#ffffff">
        <h1>
            
            <%! public void jspInit() {
        System.out.println("jspInit()");
        System.out.println(getServletContext().getInitParameter("Author"));
    }
    int myIndex;
            %>
            This is initTest.jsp, <br/>
            But it is define as a servlet in web.xml <br/>
            servlet name =<%=config.getServletName()%><br/>
            parameter region =<%=config.getInitParameter("region")%><br/>
            Date from context =<%=application.getInitParameter("Date") %><br/>
            
        </h1>
    </body>
</html>
