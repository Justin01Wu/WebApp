/*
 * sendError.java
 *
 * Created on November 6, 2006, 3:46 PM
 */

package scwcd;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author justinwu
 * @version
 */
public class AfterSendError extends HttpServlet {
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1456457678L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        System.out.println("Inside doPost");
        PrintWriter out = response.getWriter();
        out.println("Hello, ");
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unable to get name.");
            //if the buffer is large enough to accumulate all the data ("Hello..")
            // before sending it to the client,It will not throw any exception.
        
        out.println("end.");
        //if you write to the response after calling sendError() 
        // then the data is ignored and no exception is thrown. 
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        System.out.println("Inside doPost");
        PrintWriter out = response.getWriter();
        out.println("Hello, ");

        response.sendRedirect("index.html");
            //if the buffer is large enough to accumulate all the data ("Hello..")
            // before sending it to the client,It will not throw any exception.
            
        out.println("end.");
        //if you write to the response after calling sendRedirect() 
        // then the data is ignored and no exception is thrown. 
        
    }
    
    
}
