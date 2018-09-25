package wu.justa.proxy;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateAccessToken extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger LOG = Logger.getLogger(CreateAccessToken.class.getName());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try (OutputStream out = response.getOutputStream()) {

			String tokenId = request.getParameter("tokenId");
			String tokenPassword = request.getParameter("tokenPassword");
	  		String remoteAddr = request.getRemoteAddr();

			if(tokenId == null || !tokenId.equals("1")){

	  			LOG.finest("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);	

				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
			}
			
			if(tokenPassword == null || !tokenPassword.equals("justin719")){
				LOG.finest("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
				return;
			}
			
			String accessTokenUser = "1";   // TODO get userId from token table
			
	  		try{
	  			int userId = Integer.valueOf(accessTokenUser);
	  			InnerUser user = InnerUserService.load(userId);
	  			if(user == null){
					LOG.finest("CreateAccessToken is rejected for token "+ tokenId +" on remoteAddr = " + remoteAddr);
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
					return;
	  			}
	  			
	  		}catch(NumberFormatException | SQLException e){
				LOG.severe(e.getMessage());
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid token");
	  		}
			
			Date now= new Date();
			String accessToken = "userId---"+ accessTokenUser + "---" + (now.getTime()+ 30*60000);  // will expire in 30 minutes
			accessToken = Base64.getEncoder().encodeToString(accessToken.getBytes());    // TODO upgrade to good encryption
			
			response.addHeader("accessToken", accessToken);
			response.addHeader("accessTokenUser", accessTokenUser);
			response.addHeader("accessTokenExpiredAt", String.valueOf(now.getTime()));


		}
	}
}
