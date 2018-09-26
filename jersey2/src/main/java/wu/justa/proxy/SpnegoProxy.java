package wu.justa.proxy;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;

public class SpnegoProxy extends GeneralHttpProxy {
	public static final String KEY_AUTH_USER = "CurrentUser";
	
	private static final Logger log = Logger.getLogger(SpnegoProxy.class.getName());
	
	@Override
	protected String getValidOriginalUrlStart(){
		return "api";
	}
	
	@Override
	protected void copyRequestHeaders(HttpServletRequest httpRequest, HttpRequest proxyRequest  ) {
		
		super.copyRequestHeaders(httpRequest, proxyRequest);
		
		// get user
		InnerUser au = getCurrentUser(httpRequest);
		if(au == null || au.getId() == null ){
			return;
		}

		appendInnerToken(this.getClass(), httpRequest, proxyRequest, au.getId());
	}
		
	@Override
	protected boolean isAuthenticated(HttpServletRequest servletRequest){		
  		
  		InnerUser au = getCurrentUser(servletRequest);
  				
  		if(au == null  || au.getId() == null || au.getId().equals(0)){
  			return false;
  		}
		return true;
	}
	
	
	/**
	 * 
	 * overwrite header if it exists
	 */
	public static void appendInnerToken( Class<?> clazz, HttpServletRequest httpRequest, HttpRequest proxyRequest, int userId)  {
		
	
		String headerKey   = InnerTokenFilter.INNER_TOKEN_KEY;		
		String headerValue = String.valueOf(userId) ;   
		
		if(proxyRequest.containsHeader(headerKey)){
			log.fine(" overwrite http header: " + headerKey);
			proxyRequest.setHeader(headerKey, headerValue);
		}else{
			proxyRequest.addHeader(headerKey, headerValue);
		}
		
		headerKey   = InnerTokenFilter.INNER_TOKEN_FROM;
		headerValue = clazz.getSimpleName();
		if(proxyRequest.containsHeader(headerKey)){
			log.fine(" overwrite http header: " + headerKey);
			proxyRequest.setHeader(headerKey, headerValue);
		}else{
			proxyRequest.addHeader(headerKey, headerValue);
		}		
				
	}
	
	private static InnerUser getCurrentUser(HttpServletRequest servletRequest) {
		
		InnerUser user = null;
		
//  		HttpSession httpSession = servletRequest.getSession(false);	
//  		if(httpSession == null) {
//  			return null;
//  		}
//  		InnerUser user = (InnerUser)httpSession.getAttribute(KEY_AUTH_USER);

		
		
  		try {
			user = InnerUserService.load(1);
			
	  		// this is hacking, in Spnego solution, 
	  		// browser will negotiate with Tomcat to get current user and save it into the session
	  		// but now we don't have AD, so have to hack

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("load user failed", e);
		}  
  		
  		return user;

	}
		

}