package scwcd;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;

public class getHeaders extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException,ServletException{
        
        response.setContentType("text/html");

        response.addHeader("Age","1200"); 
        // Header Field Definitions at  
        // http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
        
        
        PrintWriter out=response.getWriter();
        
        out.println("<head>");
        out.println("<meta name='servlet author' content='Justin'>");
        out.println("</head>");
        
        Enumeration<?> enu=request.getHeaders("Accept-Language");
        while(enu.hasMoreElements()){
            Object x=enu.nextElement();
            out.println("Accept-Language =" + x.toString() + " <br/>" );
        }
        
        out.println( " <br/>");
        out.println( " <br/>");
        
        Enumeration<?> headers=request.getHeaderNames();
        while(headers.hasMoreElements()){
            Object x=headers.nextElement();
            Object value=request.getHeader(x.toString());
            out.println(x.toString()+ " = "+value.toString() + " <br/>" );
        }        
        
        

    }
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws IOException,ServletException{
        
        PrintWriter out=response.getWriter();
        
         String xxx=request.getCharacterEncoding();
         out.println("CharacterEncoding= "+xxx + " <br/>" );
         int yyy=request.getContentLength();
         out.println("ContentLength = "+yyy + " <br/>");
         String zzz=request.getContentType();
         out.println("ContentType = "+zzz + " <br/>");
         
         out.println( " <br/>");
         
         String name=request.getParameter("name");
         out.println("<b>name</b> = "+name + " <br/>");
         
    }    
    
}
