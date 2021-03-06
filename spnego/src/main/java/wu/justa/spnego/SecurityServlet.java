package wu.justa.spnego;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * This Servlet will redirect to the original request with JWT token
 */
public class SecurityServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(SecurityServlet.class);

	private static final long serialVersionUID = 23423L;

	public static final String JWT_TOKEN_QUERY = "jwtToken";
	public final static String ORIGINAL_REQUEST_URL = "redirect_uri";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (log.isTraceEnabled()) {
			log.trace("Enter SecuritySerlvet process() method");
		}
		process(request, response);
	}

	/**
	 * get username from  SpnegoHttpFilter, and set it to http session
	 */
	public static void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String origUrl = request.getParameter(ORIGINAL_REQUEST_URL);
		
		if (origUrl == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		TokenUser authUser = getAuthUser(request, response);
		if(authUser == null) {
			return;			
		}
		
		if (log.isTraceEnabled()) {
			log.trace("set session attribute of 'user' with " + authUser);
		}
		
		String userName = authUser.getUserName();
		try {
			String redirectUrl = getRedirectUrl(origUrl, authUser);
			response.sendRedirect(redirectUrl);
			log.info(userName + "\t" + request.getRemoteAddr() + " \t" + "login success");
		} catch (Exception se) {
			log.error(userName + "\t" + request.getRemoteAddr() + " \tFailed login attempt");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	public static TokenUser getAuthUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String authDomainUserName = request.getRemoteUser();  // SpnegoHttpFilter set it 
		
//		Principal p = request.getUserPrincipal(); // SpnegoHttpFilter set it		
//		log.trace(p);	
		

		if (authDomainUserName == null || authDomainUserName.isEmpty()) {
			log.error(authDomainUserName + "\t" + request.getRemoteAddr() + " \tFailed login attempt");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}		
		
		String realUserName = authDomainUserName;
		String asUser = request.getParameter("as_user");
		if (asUser == null) {
			log.info(authDomainUserName + "\t" + request.getRemoteAddr() + " \t" + "try to login");	
		}else {			
			log.info(authDomainUserName + "\t" + request.getRemoteAddr() + " \t" + "try to login as " + asUser);
			authDomainUserName = asUser;
		}

		TokenUser authUser = new TokenUser();
		authUser.setUserName(authDomainUserName);
		authUser.setRealUserName(realUserName);
		return authUser;

	}
	
	private static String getRedirectUrl(String origUrl, TokenUser authUser) {
		String redirectUrl = null;
		
		String token = JWTTokenServlet.createToken2(authUser);
		if(origUrl.contains("?")) {
			redirectUrl = origUrl + "&"+ JWT_TOKEN_QUERY + "=" + token ;	
		}else {
			redirectUrl = origUrl + "?"+ JWT_TOKEN_QUERY + "=" + token ;
		}			
			
		if (log.isDebugEnabled()) {
			log.debug("redirect to: " + redirectUrl);
		}

		return redirectUrl;

	}
	

}
