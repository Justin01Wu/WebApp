package scwcd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RetrieveSourceCode extends HttpServlet {

	private static final long serialVersionUID = 14356L;

	//Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        byte[] bytearray = new byte[1024]; //1K buffer
        OutputStream os = response.getOutputStream();

        InputStream is = getInputStream(request);
        int bytesread = 0;
        while ((bytesread = is.read(bytearray)) != -1) {
            os.write(bytearray, 0, bytesread);
        }
        os.flush();
        is.close();

    }

    private InputStream getInputStream(HttpServletRequest request)
            throws ServletException, IOException {

        System.out.println(request.getPathInfo());
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURI());
        System.out.println(request.getServletPath());
        ServletContext context = getServletContext();

        String referer = request.getHeader("Referer");
        URL url2 = new URL(referer);        
        
        String sourceFile = url2.getFile();
        System.out.println("   get sourceFile from Referer: " +  sourceFile);
        sourceFile = sourceFile.substring(request.getContextPath().length());
        System.out.println("   =  > sourceFile without context =" +  sourceFile);

        URL url = context.getResource("/group1/sourceCode.jsp"); // hard code
        url = context.getResource(sourceFile); // this is right way
        // I didn't test this situation: jsp file is hidden under WEB_INF
        // and it can only be accessed through redirection
        
        // it will have a problem when context is different from physical path
        
        

        InputStream is = url.openStream();
        return is;
    }
}
