package scwcd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InitParameter extends HttpServlet {

	private static final long serialVersionUID = 56771L;
	private static final String CONTENT_TYPE = "text/html";

    //Initialize global variables
    public void init() throws ServletException {
        System.out.println(getServletContext().getInitParameter("Author"));
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType(CONTENT_TYPE);

        ServletContext servletContext=this.getServletContext();
        String initValue=servletContext.getInitParameter("Author");
        String initValue2=this.getInitParameter("xyz");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>InitParameter</title></head>");
        out.println("<body bgcolor=\"#ffffff\">");

        out.println("<br/>The application has a context-param:");
        out.println("<br/>Author="+initValue);
        out.println("<br/>The servlet has a init parameter:");
        out.println("<br/>xyz="+initValue2);
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
        //Clean up resources
            public void destroy() {
    }
}
