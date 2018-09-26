package wu.justa.proxy;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

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

	
	private static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = JWTSetting.SecretKey;  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
           .setSigningKey(key)         //设置签名的秘钥
           .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }

	public static void printJwt(Claims c){
        System.out.println("  id: " + c.getId()); //jwt Id
        System.out.println("  IssuedAt: " + c.getIssuedAt());  //Mon Feb 05 20:50:49 CST 2018
        System.out.println("  expiredAt: " + c.getExpiration());  //Mon Feb 05 20:50:49 CST 2018
        System.out.println("  subject: "+ c.getSubject());  //{id:100,name:justin.wu}
        System.out.println("  issuer: " + c.getIssuer());//null
        //System.out.println("  uid: " + c.get("uid", String.class));//DSSFAWDWADAS...
        System.out.println("  justin: " + c.get("justin", String.class));        
		
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
		
		Claims claim;
		try {
			claim = parseJWT(accessTokens[1]);
		} catch (Exception e1) {
			e1.printStackTrace(); 
			return null;
		}
		printJwt(claim);
		
  		Date now= new Date();
  		long expired = claim.getExpiration().getTime();
  		if(expired <now.getTime()){
  			return null;
  		}
  		
  		InnerUser user = null;
  		
  		try{
  			int userId = Integer.valueOf(claim.getSubject());
  			
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
