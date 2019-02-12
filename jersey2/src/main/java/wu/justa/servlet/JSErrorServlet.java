package wu.justa.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSErrorServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String msg = request.getParameter("msg");
		if (msg !=null && msg.length()>0) {
			System.out.println(msg);
		}
		
		String url = request.getParameter("url");
		if (url !=null && url.length()>0) {
			System.out.println(url);
		}
		
		String line = request.getParameter("line");
		if (line !=null && line.length()>0) {
			System.out.println(line);
		}

    }		

}
