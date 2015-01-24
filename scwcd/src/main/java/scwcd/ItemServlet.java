package scwcd;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 3456781L;

	public void doGet(HttpServletRequest req,
                      HttpServletResponse res) throws IOException,ServletException{
        try {
            throw new SQLException("connection can't be found");
            //generate HTML page using items.
        } catch (SQLException e) {
            //send an error message to the browser.
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                          "There was a demo of exception processing. ");
        }
    }
}
