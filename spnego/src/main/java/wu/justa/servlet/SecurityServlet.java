package wu.justa.servlet;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import wu.justa.model.TokenUser;

/**
 * This Servlet is the authentication and authorization for the application
 */
public class SecurityServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(SecurityServlet.class);

	private static final long serialVersionUID = 23423L;

	public static final String LOGIN_ERROR = "/jsp/common/errorLogin.jsp";
	public static final String PAGE_HOME = "/spnego/index.html";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		process(request, response);
	}

	/**
	 * get username from  SpnegoHttpFilter, and set it to http session
	 */
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (log.isTraceEnabled()) {
			log.trace("Enter SecuritySerlvet process() method");
		}

		String authDomainUserName = request.getRemoteUser();  // SpnegoHttpFilter set it 
		
		Principal p = request.getUserPrincipal(); // SpnegoHttpFilter set it
		
		log.info(p);
		
		// Spnego set it for us
		
		HttpSession session = request.getSession();

		if (authDomainUserName == null || authDomainUserName.isEmpty()) {

			log.error(authDomainUserName + "\t" + request.getRemoteAddr() + " \tFailed login attempt");
			return;
		}
		;

		log.info(authDomainUserName + "\t" + request.getRemoteAddr() + " \t" + "try to login");

		String authUserName = null;

		try {

			TokenUser authUser = new TokenUser();
			authUser.setUserName(authDomainUserName);

			if (log.isTraceEnabled()) {
				log.trace("set session attribute of 'user' with " + authUser);
			}

			// Loading privilege information here
			session.setAttribute(ClickstreamFilter.session_user, authUser);

			String redirectUrl = null;
			String origUrl = request.getParameter(ClickstreamFilter.ORIGINAL_REQUEST_URL);
			if (origUrl != null) {

				// redirectUrl = URLDecoder.decode(origUrl, "UTF-8");
				String token = JWTTokenServlet.createToken2(authUser);
				if(origUrl.contains("?")) {
					redirectUrl = origUrl + "&token=" + token ;	
				}else {
					redirectUrl = origUrl + "?token=" + token ;
				}				 
				
				if (log.isDebugEnabled()) {
					log.debug("redirect to: " + redirectUrl);
				}
			} else {
				redirectUrl = PAGE_HOME;
			}
			response.sendRedirect(redirectUrl);

			log.info(authDomainUserName + "\t" + request.getRemoteAddr() + " \t" + "login success");

		} catch (Exception se) {
			log.log(Level.FATAL, authUserName + "\t" + request.getRemoteAddr() + " \tValidate User Login", se);
			log.error(authUserName + "\t" + request.getRemoteAddr() + " \tFailed login attempt");
			dispatch(request, response, LOGIN_ERROR);
		}
	}

	/**
	 * Handling session attributes and redirections.
	 * 
	 * @param request  the HttpServletRequest
	 * @param response the HttpServletResponse
	 * @param nextpage the nextpage
	 * 
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String nextpage)
			throws ServletException, IOException {
		RequestDispatcher disp = getServletContext().getRequestDispatcher(nextpage);
		disp.forward(request, response);
	}
}
