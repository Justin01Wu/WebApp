package scwcd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html";

    //Initialize global variables
    public void init() throws ServletException {
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter(); 
            out.println("<html>");

            out.println("<body>");
            out.println("<h1>The servlet has received a exception. This is the detail:</h1>");

            Object status_code =request.getAttribute("javax.servlet.error.status_code");
            out.println("status_code="+status_code);
            out.println("<br/>");

            Object exception_type =request.getAttribute("javax.servlet.error.exception_type");
            out.println("exception_type="+exception_type);
            out.println("<br/>");

            Object exception =request.getAttribute("javax.servlet.error.exception");
            Throwable t=(java.lang.Throwable)exception;
            out.println("Message="+t.toString());
            out.println("<br/>");

            out.println("StackTrace:<br/>");
            t.printStackTrace(out);
            out.println("<br/>");

            Throwable t2=t.getCause();
            if(t2!=null){
                t2.printStackTrace(out);
                out.println("<br/>");
            }

            if(t instanceof ServletException){
               Throwable t3=((ServletException)t).getRootCause();
                t3.printStackTrace(out);
                out.println("<br/>");
            }
            
            out.println("</body>"); out.println("</html>"); out.close(); }
            //Clean up resources
            public void destroy() {
    }
}
