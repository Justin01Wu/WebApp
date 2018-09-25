package wu.justa.proxy;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;

public class AccessTokenProxy extends GeneralHttpProxy {
	
	private static Logger LOG = Logger.getLogger(AccessTokenProxy.class.getName());
	
	@Override
	protected void copyRequestHeaders(HttpServletRequest httpRequest, HttpRequest proxyRequest  ) {
		
		super.copyRequestHeaders(httpRequest, proxyRequest);
		
		// get user
		String accessTokenUser = httpRequest.getHeader("accessTokenUser");
		int userId = Integer.valueOf(accessTokenUser);
		
		SpnegoProxy.appendInnerToken(this.getClass(), httpRequest, proxyRequest, userId); 
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


		
	@Override
	protected boolean isAuthenticated(HttpServletRequest servletRequest){		
  		
		String accessToken = servletRequest.getHeader("accessToken");
		String accessTokenUser = servletRequest.getHeader("accessTokenUser");
		if(accessToken == null || accessTokenUser == null){
			return false;
		}
		
		accessToken = new String(Base64.getDecoder().decode(accessToken));  // TODO upgrade to good encryption
		String[] segs = accessToken.split("---");
  		if(segs.length != 3){
  			return false;
  		}
  		if(!segs[0].equals("userId")){
  			return false;
  		}
  		Date now= new Date();
  		long expired = Long.valueOf(segs[2]);
  		if(expired <now.getTime()){
  			return false;
  		}
  		if(!accessTokenUser.equals(segs[1])){
  			return false;
  		}
  		
  		try{
  			int userId = Integer.valueOf(accessTokenUser);
  			
  			InnerUser user = InnerUserService.load(userId);
  			if(user == null){
  				return false;
  			}  			
  			
  		}catch(NumberFormatException | SQLException e){
  			LOG.severe( e.getMessage());
  			return false;
  		}
		return true;
	}

}
