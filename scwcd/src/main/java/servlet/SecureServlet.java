/*
 * SecureServlet.java
 *
 * Created on November 7, 2006, 12:32 PM
 */

package servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author justinwu
 * @version
 */
public class SecureServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        PrintWriter pw = response.getWriter();
        pw.println("<html><head>");
        pw.println("<title>Programatic Security Example</title>");
        pw.println("</head>");
        pw.println("<body>");
        String username = request.getRemoteUser();
        if(username != null)
            pw.println("<h4>Welcome, "+username+"!</h4>");
        if(request.isUserInRole("director")) {
            pw.println("<b>Director's Page!</b>");
        } else   {
            pw.println("<b>Employee's Page!</b>");
        }
        pw.println("</body></html>");
    }
    

    
    
}
