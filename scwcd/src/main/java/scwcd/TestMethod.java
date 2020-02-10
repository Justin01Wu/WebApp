package scwcd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestMethod extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html";

    //Initialize global variables
	@Override
    public void init() throws ServletException {
        System.out.println("TestMethod.init() is called automatically because of load-on-startup");
        
        // start an infinite loop thread
        Runnable r = new ThreadTest();
        Thread t1 = new Thread(r, "ThreadTest from TestMethod.init");
        t1.start();
    }

    //Process the HTTP Get request
	@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter(); out.println("<html>");
        out.println("<head><title>TestMethod</title></head>");
        out.println("<body bgcolor=\"#ffffff\">");
        out.println("<body bgcolor='#ffffff'>");
        
        out.println("<h1><p>The servlet has received a " + request.getMethod() +".<br/>");
        out.println("Please see the URL, all inputed data from previous form is inside. </p></hi>"); 
        
        out.println("</body>");

        out.println("</html>"); out.close();
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }


    //Clean up resources
    @Override
    public void destroy() {
        // I can't find a way to ask container to execute this except shutdown
        // the container
        System.out.println("TestMethod.destroy()");
        log("destroy TestMethod servlet...");
    }
}
