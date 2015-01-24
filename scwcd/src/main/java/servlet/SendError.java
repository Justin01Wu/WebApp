package servlet;
/*
 * SendError.java
 *
 * Created on November 2, 2006, 9:28 AM
 */

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author justinwu
 * @version
 */
public class SendError extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	/** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        /*
        Map numberMap=new HashMap();
        numberMap.put("key_1","value_1");
        numberMap.put("key_2","value_2");
        numberMap.put("key_3","value_3");
        request.setAttribute("numberMap",numberMap);
         */
        try {
            throw new SQLException("connection can't be found");
            //generate HTML page using items.
        } catch (SQLException e) {
            //send an error message to the browser.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                          "There was a demo of exception processing. ");
        }
        
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
