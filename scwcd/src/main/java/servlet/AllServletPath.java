/*
 * AllServletPath.java
 *
 * Created on November 17, 2006, 11:19 AM
 */

package servlet;

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
public class AllServletPath extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 455677681L;



	//Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<body");
        
        out.println("<h3>");
        if(this.getInitParameter("noway")!=null)  {
            out.println("<b>No Way to come to this servlet </b>");
        }else{
            out.println("<b>When url-pattern is : </b>/eachServletPath/xxx/*");
            out.println("<br/><br/>");
            
            
            out.println("<b>request.getContextPath: </b>"+request.getContextPath());
            out.println("<br/>");
            out.println("<b>request.getServletPath: </b>"+request.getServletPath());
            out.println("<br/>");
            out.println("<b>request.getPathInfo: </b>"+request.getPathInfo());
            out.println("<br/><br/>");
            
            out.println("<b>request.getLocalAddr: </b>"+request.getLocalAddr());
            out.println("<br/>");
            out.println("<b>request.getLocalPort: </b>"+request.getLocalPort());
            out.println("<br/>");
            out.println("<b>request.getLocalName: </b>"+request.getLocalName());
            out.println("<br/><br/>");

            out.println("<b>request.getProtocol: </b>"+request.getProtocol());
            out.println("<br/>");
            out.println("<b>request.getAuthType: </b>"+request.getAuthType());
            out.println("<br/>");            
            out.println("<b>request.getMethod: </b>"+request.getMethod());
            out.println("<br/>");            

            out.println("<b>request.getServerName: </b>"+request.getServerName());
            out.println("<br/>");
            out.println("<b>request.getServerPort: </b>"+request.getServerPort());
            out.println("<br/><br/>");
            
            out.println("<b>request.getPathTranslated: </b>"+request.getPathTranslated());
            out.println("<br/>");
            out.println("<b>getServletContext().getRealPath('.'): </b>"+getServletContext().getRealPath("."));
            out.println("<br/>");
            out.println("<b>getServletContext().getServerInfo(): </b>"+getServletContext().getServerInfo());
            out.println("<br/>");
            
            out.println("<b>getServletContext().getServletContextName(): </b>"+getServletContext().getServletContextName());
            out.println("<br/>");
            
            out.println("<b>getServletConfig().getServletName(): </b>"+getServletConfig().getServletName());
            out.println("<br/>");
            out.println("<b>getServletInfo(): </b>"+getServletInfo());
            out.println("<br/>");
            
            out.println("<b>request.getRequestURL(): </b>"+request.getRequestURL());
            out.println("<br/>");
            
            out.println("<b>request.getRequestURI(): </b>"+request.getRequestURI());
            out.println("<br/><br/>");
            
            
        }
        
        out.println("<a href='/index.html'>return home page</a>");
        
        out.println("<h3>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    
    
    
    public String getServletInfo() {
        return "AllServletPath.getServletInfo";
    }
    
}
