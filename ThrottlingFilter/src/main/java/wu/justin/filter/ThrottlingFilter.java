package wu.justin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThrottlingFilter implements Filter {
	
	private static Controller controller = Controller.getInstance();
	private static String contextUrl; 

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if(request instanceof HttpServletRequest && response instanceof HttpServletResponse){
			HttpServletRequest  httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse =(HttpServletResponse) response;

			if(controller.hasDelayQueue()){							
				if(httpRequest.getRequestURI().startsWith(contextUrl + controller.getDelayUrl())){
					System.out.println("delay seconds for " + httpRequest.getRequestURI());
					try {
						Thread.sleep(controller.getDelayMillionSecond());
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}					
				}
			}
			if(controller.hasErrorQueue()){
				httpResponse.sendError(controller.getErrorCode(), "ThrottlingFilter set error code for testing");
				return;
			}
		}
		
		chain.doFilter(request, response);		
		
	}

	public void init(FilterConfig config) throws ServletException {
		contextUrl = config.getServletContext().getContextPath();		
		
	}
	
}