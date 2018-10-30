package wu.justa.proxy;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateAccessToken extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = Logger.getLogger(CreateAccessToken.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try (OutputStream out = response.getOutputStream()) {

			String tokenId = request.getParameter("tokenId");
			String tokenPassword = request.getParameter("tokenPassword");
	  		String remoteAddr = request.getRemoteAddr();

			if(tokenId == null || !tokenId.equals("1")){

	  			LOG.trace("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);	

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
			}
			
			if(tokenPassword == null || !tokenPassword.equals("justin719")){
				LOG.trace("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
			}
			
			String accessTokenUser = "1";   // TODO get userId from token table
			InnerUser user = null;
	  		try{
	  			int userId = Integer.valueOf(accessTokenUser);
	  			user = InnerUserService.load(userId);
	  			if(user == null){
					LOG.trace("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
					return;
	  			}
	  			
	  		}catch(NumberFormatException e){
				LOG.error(e.getMessage());
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
	  		}catch(SQLException e){
				LOG.error(e.getMessage());
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "invalid token");
				return;
	  		}
			
			Date now= new Date();
			
			long expiredTime =  now.getTime()+ 30*60000;   // will expire in 30 minutes
			
			String accessToken = null;
			try {
				accessToken = createJWTToken(user, expiredTime);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "failed on token creating");
				return;
			}
			
			Map<String, String> restData = new HashMap<>();
			
			restData.put("access_token", accessToken);
			restData.put("expired", String.valueOf(expiredTime));
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(restData);			
			
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
		    out.write(jsonInString.getBytes("UTF-8"));        
		    out.flush();
		    

		}
	}
	
	// comes from https://blog.csdn.net/qq_37636695/article/details/79265711
	public String createJWTToken(InnerUser user, long ttlMillis) throws Exception {
		
        //String tokenId = UUID.randomUUID().toString();
        
        
        String token = JwtUtil.createToken(user.getId(), user.getName(), ttlMillis);
        return token;
        
    }
}
