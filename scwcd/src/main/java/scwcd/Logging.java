package scwcd;

import java.security.GeneralSecurityException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**  This is a demo to show us what kind of output information can be recorded
 * by the servlet container. In specification, log output must be recorded.
 *
 * In Tomcat 4.1 , only log output information is recorded ,
 * printStackTrace and System.out.println can only be seen from console
 * by the way, log output information can't be seen from console.
 *
 * in some servlet containers, printStackTrace and System.out.println can be
 * recorded also.
 */
public class Logging extends HttpServlet {

	private static final long serialVersionUID = 4354354351L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException {

        ServletContext sc = this.getServletContext();
        sc.log("ServletContext.log()");

        try {
            throw new GeneralSecurityException(" this is my 'exception'");
        } catch (GeneralSecurityException e) {
            
            //log the exception
            log("Exception in servlet", e);

            log("printStackTrace start:");
            e.printStackTrace();            
            log("printStackTrace end.");
            
            System.out.println("System.out.println:" + e.getMessage());
            
            //wrap the exception in a ServletException and rethrow
            throw new ServletException("Wrapped GeneralSecurityException by Justin", e);
            
        }
    }

}
