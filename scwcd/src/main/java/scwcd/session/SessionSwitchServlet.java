package scwcd.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionSwitchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * demo how to switch session ID, this way we can protected from some
	 * session attacking
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession oldSession =  request.getSession();

		// get all attributes from old session
		Map<String, Object> attrs = new HashMap<String, Object>();
		Enumeration<?> attrNames = oldSession.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = (String) attrNames.nextElement();
			attrs.put(attrName, oldSession.getAttribute(attrName));
		}

		String oldSessionId = oldSession.getId();
		oldSession.invalidate();                             // stop old session
		HttpSession newSession =  request.getSession(true);  // create a new session

		for (Map.Entry<String, Object> entry : attrs.entrySet()) {
			newSession.setAttribute(entry.getKey(), entry.getValue());
		}

		PrintWriter pw = response.getWriter();
		pw.println("<html><head>");
		pw.println("<title>session id switch example</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1>  This page always change your session id !</h1>");
		pw.println("<h2>  demo how to switch session ID, this way we can protected from some session attacking</h2>");

		pw.println("<h2>  old session id is " + oldSessionId + "</h2>");
		pw.println("<h2>  new session id is " + newSession.getId() + "</h2>");
		pw.println("<h2>  response header will have set-Cookie for new session id " );

		pw.println("</body></html>");
	}

}
