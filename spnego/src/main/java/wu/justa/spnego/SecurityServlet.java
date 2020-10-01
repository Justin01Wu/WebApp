package wu.justa.spnego;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * This Servlet is the authentication user, find who you are on Spnego protocol
 */
public class SecurityServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(SecurityServlet.class);

	private static final long serialVersionUID = 23423L;

	public static final String PAGE_HOME = "/spnego/";
	public static final String JWT_TOKEN_QUERY = "jwtToken";

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

		TokenUser authUser = getAuthUser(request, response);
		if(authUser == null) {
			return;			
		}
		
		if (log.isTraceEnabled()) {
			log.trace("set session attribute of 'user' with " + authUser);
		}
		HttpSession session = request.getSession();
		session.setAttribute(ClickstreamFilter.session_user, authUser);
		
		String userName = authUser.getUserName();
		try {
			String redirectUrl = getRedirectUrl(request, authUser);
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

		log.info(authDomainUserName + "\t" + request.getRemoteAddr() + " \t" + "try to login");

		String authUserName = null;
		TokenUser authUser = new TokenUser();
		authUser.setUserName(authDomainUserName);
		return authUser;

	}
	
	private static String getRedirectUrl(HttpServletRequest request, TokenUser authUser) {
		String redirectUrl = null;
		String origUrl = request.getParameter(ClickstreamFilter.ORIGINAL_REQUEST_URL);
		
		if (origUrl != null) {

			if(isSameOrig(request, origUrl)){
				// same orig
				redirectUrl = origUrl;
			}else {
				// different orig
				String token = JWTTokenServlet.createToken2(authUser);
				if(origUrl.contains("?")) {
					redirectUrl = origUrl + "&"+ JWT_TOKEN_QUERY + "=" + token ;	
				}else {
					redirectUrl = origUrl + "?"+ JWT_TOKEN_QUERY + "=" + token ;
				}				
			}
			
		} else {
			redirectUrl = PAGE_HOME;
			//throw new RuntimeException("Justin test2345");
		}
		if (log.isDebugEnabled()) {
			log.debug("redirect to: " + redirectUrl);
		}

		return redirectUrl;

	}
	
	private static boolean isSameOrig(HttpServletRequest request, String uri) {
					
			String contextURL = request.getScheme() 
				+ "://" + request.getServerName() 
				+ ":" + request.getServerPort() 
				+ request.getContextPath();
			
			if(uri.startsWith(contextURL)) {
				return true;
			}
			return false;
		
	}


}
