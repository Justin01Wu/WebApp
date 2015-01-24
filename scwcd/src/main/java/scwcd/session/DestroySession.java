/*
 * DestroySession.java
 *
 * Created on September 27, 2006, 4:00 PM
 */

package scwcd.session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author justinwu
 * @version
 */
public class DestroySession extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	/** Even is a session is invalidated (say it times out), 
     * the page can still access the session variable.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session=request.getSession();
        session.invalidate();
        //boolean isNew=session.isNew();   this will throw exception
       String id=session.getId();
       System.out.println("sessionId= "+id);
        //session.setAttribute("sss","eer");  this will throw exception
        
        response.setContentType("text/html;charset=UTF-8");

        Cookie cookie=new Cookie("Justin719","DestroySession set cookie");
        response.addCookie(cookie);
    
        PrintWriter out = response.getWriter();
        // TODO output your page here
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet DestroySession</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet DestroySession at " + request.getContextPath () + "</h1>");
        out.println("</body>");
        out.println("</html>");
         
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
