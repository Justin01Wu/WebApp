package listener;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorldFilter implements Filter {
    private FilterConfig filterConfig;
    //Handle the passed-in FilterConfig
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    //Process the request/response pair
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
   throws ServletException,IOException {
        /*
        try {
            filterChain.doFilter(request, response);
        } catch (ServletException sx) {
            filterConfig.getServletContext().log(sx.getMessage());
        } catch (IOException iox) {
            filterConfig.getServletContext().log(iox.getMessage());
        }
        */
       PrintWriter pw=response.getWriter();
       pw.println("<html>");
       pw.println("<head>");
       pw.println("</heda>");
       pw.println("<body>");
       pw.println("<h3>The application doesn't have filter/helloworldfilter!</h3>");
       pw.println("<h3>The page is created by HelloWorldFilter</h3>");

       pw.println("</body>");
       pw.println("</html>");

    }

    //Clean up resources
    public void destroy() {
    }
}
