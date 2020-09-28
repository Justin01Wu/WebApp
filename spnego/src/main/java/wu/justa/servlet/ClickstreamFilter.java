package wu.justa.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import wu.justa.model.VcapsUser;

/**
 * check if http session is valid
 */
public class ClickstreamFilter implements Filter {
	
	private static final Logger log = Logger.getLogger(ClickstreamFilter.class);  
	protected FilterConfig filterConfig;
	public final static String ORIGINAL_REQUEST_URL = "_original_request_uri";
	
	public final static String session_user = "session_user";
	  
	public void init(FilterConfig config) throws ServletException {
		this.filterConfig = config;
	}
	  
  	public void doFilter(ServletRequest request, ServletResponse response,
	                   FilterChain chain) throws IOException, ServletException {
	     	   
  		HttpServletRequest httpRequest=(HttpServletRequest) request;
  		HttpServletResponse httpResponse = (HttpServletResponse)response;
  		
  		String uri = httpRequest.getRequestURI().toString();
  		
  		if(log.isTraceEnabled()){
	  		log.trace(uri);
  		}
  		
  		if(isBackDoors(uri)){
  			chain.doFilter(request, response);
  			return;
  		}
  		
  		HttpSession httpSession = httpRequest.getSession();  		
  			
  		boolean isHttpSessionValid = false;

  		VcapsUser au = (VcapsUser)httpSession.getAttribute(ClickstreamFilter.session_user);
  				
  		if(au != null){
  			isHttpSessionValid = true;
  		}
  		  			
  		if(!isHttpSessionValid){
  			if(log.isDebugEnabled()){
  				log.debug("invalid HttpSession, so redirect to login page from " + uri);  
  			}  				
		
  			String targetUrl = getTargetUrl(httpRequest);
  			if(targetUrl == null){  				
  				httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please relogin before do anything");
  				return;
  			}
			httpResponse.sendRedirect(targetUrl);  				
  				
  			return;
  		}

	    // pass the request on
	    chain.doFilter(request, response);
	  }
	  
  	public void destroy() { }
	
  	
  	public void dispatch(HttpServletRequest request,
  	        HttpServletResponse response, String nextpage) throws
  	    ServletException, IOException {
  		RequestDispatcher disp = request.getSession().getServletContext().getRequestDispatcher(nextpage);
  		disp.forward(request, response);
  	}
  	
  	private boolean isBackDoors(String uri){
  		
  		if( ( uri.indexOf("unexpectedErrorReal.jsf") > 0 )
  				|| ( uri.indexOf("unexpectedError.jsf") > 0 )
  				|| (uri.indexOf("SecurityServlet")>0)
  				|| (uri.indexOf("login.jsp")>0)  				
  				|| uri.endsWith(".js")
  				|| uri.endsWith(".css")
  		){
  			return true;
  		}
  		return false;
  		
  	}
  	
  	private static String getTargetUrl(HttpServletRequest httpRequest) throws UnsupportedEncodingException{
  		
  		String uri = httpRequest.getRequestURI().toString();
  		String targetUrl ;
  		
		String method = httpRequest.getMethod();
		if(!"GET".equals(method)){			
			return null;
		}

  		if(uri.equals(httpRequest.getContextPath()+"/")){
  			targetUrl = "/spnego/SecurityServlet";
  		}else{
  			String orgRequestUrl = getFullUrl(httpRequest);
  			if(log.isDebugEnabled()){
  				log.debug("remember url for future redirecting: " + orgRequestUrl);	
  			}
  			targetUrl = "/spnego/SecurityServlet?" + ORIGINAL_REQUEST_URL + "=" + URLEncoder.encode(orgRequestUrl, "UTF-8");
  		}
  		
		if(log.isDebugEnabled()){
			log.debug("targetUrl: " + targetUrl);	
		}	
  		
  		return targetUrl;

  	}
  	
	static String getFullUrl(HttpServletRequest request) {
  	    StringBuffer requestURL = request.getRequestURL();
  	    String queryString = request.getQueryString();

  	    if (queryString == null) {
  	        return requestURL.toString();
  	    } else {
  	        return requestURL.append('?').append(queryString).toString();
  	    }
  	}

}  
