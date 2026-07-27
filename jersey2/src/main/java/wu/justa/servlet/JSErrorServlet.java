package wu.justa.servlet;

import java.util.logging.Logger;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 

public class JSErrorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 15464563L;
	
	private final static Logger log =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String msg = request.getParameter("msg");
		if (msg !=null && msg.length()>0) {
			log.warning(msg);
		}
		
		String url = request.getParameter("url");
		if (url !=null && url.length()>0) {
			log.warning(url);
		}
		
		String line = request.getParameter("line");
		if (line !=null && line.length()>0) {
			log.warning(line);
		}

    }		

}
