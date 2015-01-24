<html>
    <head>
        <title>
            sqlError
        </title>
    </head>
    <body bgcolor="#ffffff">
        <h1>
            JSP page without errorPage throws a Wrapped SQLException
            <%
            if (true) {
                java.sql.SQLException e = new java.sql.SQLException("sss");
                throw new ServletException("Wrapped SQLException", e);
            }
            %>
        </h1>
    </body>
</html>
