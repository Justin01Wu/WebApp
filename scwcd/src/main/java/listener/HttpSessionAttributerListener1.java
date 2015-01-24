package listener;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;


/**an attribute is modified in the session*/
public class HttpSessionAttributerListener1 extends HttpServlet implements
        HttpSessionAttributeListener {
    
	private static final long serialVersionUID = 1L;

	//Notification that a new attribute AFTER it is added to a session
    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println("");
        System.out.println("HttpSessionAttributerListener1.attributeAdded:");
        System.out.println("  A bean has been added to current session: "+se.getName());
    }

    //Notification that an attribute AFTER it is removed from a session
    public void attributeRemoved(HttpSessionBindingEvent se) {
        System.out.println("");
        System.out.println("HttpSessionAttributerListener1.attributeRemoved:");
        System.out.println("  A bean has been removed from current session: "+se.getName());
    }

    //Notification that an attribute of a session has been replaced
    public void attributeReplaced(HttpSessionBindingEvent se) {
        throw new java.lang.UnsupportedOperationException(
                "Method attributeReplaced() not yet implemented.");
    }
}
