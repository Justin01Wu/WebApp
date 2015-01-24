package listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class HttpSessionListener1 implements
        HttpSessionListener {
    //Notification that a session was created
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("");
        System.out.println("HttpSessionListener.sessionCreated: ");        
        System.out.println("  Session Id = "+ se.getSession().getId());
    }

    //Notification that a session was invalidated
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("");
        System.out.println("HttpSessionListener.sessionDestroyed: ");        
        System.out.println("  Session Id = "+ se.getSession().getId());        
    }
}
