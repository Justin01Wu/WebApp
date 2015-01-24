package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import testjsp.SimpleBean;

public class ControlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ControlServlet() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static final String CONTENT_TYPE = "text/html";

    //Initialize global variables
    public void init() throws ServletException {
    }

    //Process the HTTP Post request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doPost(request,response);
    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        String url = request.getServletPath(); //   /applysubmit
        String url2 = request.getPathInfo(); //null
        String url3 = request.getRequestURI(); //  /sgnt/applysubmit
        url3 = request.getPathTranslated(); //null
        url3 = request.getProtocol(); //HTTP/1.1
        url3 = request.getQueryString(); //null
        url3 = request.getRequestURL().toString();
        // if direct to  this is http://localhost/:8080/sgnt/applysubmit
        // if forward ,this is http://localhost/:8080/sgnt/opportunities/forward.jsp


        HttpSession s = request.getSession(false);

        //pageContext.getAttribute("bean0", bean0, PageContext.SESSION_SCOPE);
        //SimpleBean apply=(SimpleBean)s.getServletContext().getAttribute("bean0");

        SimpleBean apply = (SimpleBean) request.getAttribute("bean0");
        if(apply==null){
            apply = (SimpleBean) s.getAttribute("bean0");
        }
        System.out.println("bean0.name=" + apply.getName());
        System.out.println("bean0.email=" + apply.getEmail());

        System.out.println("Parameter.interests=" +
                           request.getParameter("interests"));
        System.out.println("Parameter.name=" + request.getParameter("name"));
        System.out.println("Parameter.email=" + request.getParameter("email"));

//        SMTPDealer smtpdealer = new SMTPDealer();
//        smtpdealer.sendMail("justinwu@terida.net", request);

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Servlet1</title></head>");
        out.println("<body bgcolor=\"#ffffff\">");
        out.println(
                "<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    //Clean up resources
    public void destroy() {
    }

    private void jbInit() throws Exception {
    }
}
