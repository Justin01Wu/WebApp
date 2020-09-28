package wu.justa.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import wu.justa.model.VcapsUser;

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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		process(request, response);
	}

	/**
	 * Process to see if the user's input matches a record in database table If
	 * matches, user will be navigate to home page other wise, user will be remind
	 * on the login page.
	 * 
	 * @param request  the request
	 * @param response the response
	 * 
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (log.isTraceEnabled()) {
			log.trace("Enter SecuritySerlvet process() method");
		}

		String authDomainUserName = request.getRemoteUser();  // SpnegoHttpFilter set it 
		HttpSession session = request.getSession();

		if (authDomainUserName == null || authDomainUserName.isEmpty()) {

			log.error(authDomainUserName + "\t" + request.getRemoteAddr() + " \tFailed login attempt");
			return;
		}
		;

		log.info(authDomainUserName + "\t" + request.getRemoteAddr() + " \t" + "try to login");

		String authUserName = null;

		try {

			VcapsUser authUser = new VcapsUser();
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
				redirectUrl = origUrl; // looks like request.getParameter already decoded it
				// VCAPSAGILE-1633 SSO forward URL without properly URL encoding

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
