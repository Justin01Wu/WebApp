package wu.justa.proxy;

import java.sql.SQLException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;

import com.auth0.jwt.interfaces.DecodedJWT;

public class AccessTokenProxy extends GeneralHttpProxy {
	
	private static Logger LOG = Logger.getLogger(AccessTokenProxy.class.getName());
	
	@Override
	protected void copyRequestHeaders(HttpServletRequest httpRequest, HttpRequest proxyRequest  ) {
		
		super.copyRequestHeaders(httpRequest, proxyRequest);
		
		// get user
		InnerUser user = getAuthenticatedUser(httpRequest);
		
		SpnegoProxy.appendInnerToken(this.getClass(), httpRequest, proxyRequest, user.getId()); 
	}
	
	@Override
	protected String getValidOriginalUrlStart(){
		return "tokenApi";
	}
	
	protected boolean postAuthenticationFail(){
		return false; 
		// decide if go to next filter, 
		// if true, then go to the next filter
		// if false. then return 401 error
	}
	
	private static InnerUser getAuthenticatedUser(HttpServletRequest servletRequest) {
		String accessToken = servletRequest.getHeader("Authorization");		
		
		if(accessToken == null || accessToken.isEmpty()){
			return null;
		}
		if(!accessToken.startsWith("Bearer ")) {
			return null;
		}
		
		String[] accessTokens = accessToken.split("\\s+");  // split on all white space
		if(accessTokens.length != 2) {
			return null;
		}
		
		DecodedJWT decodedJWT = null;
		try {
			decodedJWT = JwtUtil.verifyToken(accessTokens[1]);	
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		JwtUtil.printToken(decodedJWT);
		
  		InnerUser user = null;
  		
  		try{
  			int userId = Integer.valueOf(decodedJWT.getSubject());
  			
  			user = InnerUserService.load(userId);
  			if(user == null){
  				return null;
  			}  			
  			
  		}catch(NumberFormatException | SQLException e){
  			LOG.severe( e.getMessage());
  			return null;
  		}
		return user;

	}
		
	@Override
	protected boolean isAuthenticated(HttpServletRequest servletRequest){		
		
		InnerUser user = getAuthenticatedUser(servletRequest);
		if(user == null) {
			return false;
		}
		return true;
	}

}
