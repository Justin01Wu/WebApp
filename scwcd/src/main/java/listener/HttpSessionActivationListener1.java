package listener;

import javax.servlet.http.*;

public abstract class HttpSessionActivationListener1 implements
        HttpSessionActivationListener {
    /**
     * sessionDidActivate
     *
     * @param httpSessionEvent HttpSessionEvent
     * @todo Implement this javax.servlet.http.HttpSessionActivationListener
     *   method
     */
    public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("HttpSessionActivationListener1.sessionDidActivate ");
    }

    /**
     * sessionWillPassivate
     *
     * @param httpSessionEvent HttpSessionEvent
     * @todo Implement this javax.servlet.http.HttpSessionActivationListener
     *   method
     */
    public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
        System.out.println("HttpSessionActivationListener1.sessionWillPassivate ");
    }
}
