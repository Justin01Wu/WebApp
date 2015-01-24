package scwcd.session;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import listener.*;

public class FireSession extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=GBK";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        HttpSession session = request.getSession();

        long x = session.getCreationTime();
        long y = session.getLastAccessedTime();

        //System.out.println(session.getId());

        HttpSessionBindingListener1 aUser = new HttpSessionBindingListener1();
        aUser.setUserId(11);
        aUser.setUserName("Justin719_");
        aUser.setPassword("password");
        session.setAttribute("user", aUser);

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body");
        out.println("<h2>This servlet has added an object to current session as name 'user'. </h2>");
        out.println("<br/>");

        out.println("<h2>The session is created at:</h2>" + new Date(x));
        out.println("<br/>");
        out.println("<h2>The session is accessed at:</h2>" + new Date(y));
        out.println("<br/>");

        String context = request.getContextPath();
        out.println("<a href='" + context + "/index.html'>return home page</a>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    //Clean up resources
    public void destroy() {
    }
}
