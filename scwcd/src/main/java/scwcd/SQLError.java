package scwcd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SQLError extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html";

    //Initialize global variables
    public void init() throws ServletException {
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>SQLError</title></head>");
        out.println("<body bgcolor=\"#ffffff\">");
        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body>");
        out.println("</html>");

        String initValue2 = request.getParameter("type");
        if ("IOException".equals(initValue2)) {
            IOException e = new IOException("sss");
            throw new ServletException("Wrapped IOException", e);
        } else {
            java.sql.SQLException e = new java.sql.SQLException("sss");
            throw new ServletException("Wrapped SQLException", e);

        }
    }
    //Clean up resources
    public void destroy() {
    }
}
