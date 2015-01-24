/*
 * ContextAttributerListener1.java
 *
 * Created on October 31, 2006, 11:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package listener;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;


/**
 *
 * One listener class can be used to listen for multiple kinds of events   
    if it implements multiple listener interfaces. 
 */
public class ServletComboListener 
        implements ServletContextAttributeListener,ServletRequestListener {
    
    public void attributeAdded(ServletContextAttributeEvent se) {
        System.out.println("ServletComboListener.attributeAdded(ServletContextAttributeEvent se)");
    }

    //Notification that an attribute has been removed from a session
    public void attributeRemoved(ServletContextAttributeEvent se) {
        System.out.println("ServletComboListener.attributeRemoved(ServletContextAttributeEvent se)");
    }

    //Notification that an attribute of a session has been replaced
    public void attributeReplaced(ServletContextAttributeEvent se) {
        throw new java.lang.UnsupportedOperationException(
                "ServletComboListener.attributeReplaced(ServletContextAttributeEvent se)");
    }
    
    //The request is about to go out of scope of the web application.
    public void requestDestroyed(ServletRequestEvent sre){
        HttpServletRequest request =(HttpServletRequest)sre.getServletRequest();
        String key ="javax.servlet.forward.request_uri";  // servlet spec 2.4
        String currentPageName =(String)request.getAttribute(key);// tomcat bug 28222
        if(currentPageName==null){
            currentPageName=""+request.getRequestURL();
        }          
        System.out.println("ServletComboListener.requestDestroyed:");
        System.out.println("   A request has been destroyed "+currentPageName);
    }
          
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request =(HttpServletRequest)sre.getServletRequest();
        String key ="javax.servlet.forward.request_uri";  // servlet spec 2.4
        String currentPageName =(String)request.getAttribute(key);// tomcat bug 28222
        if(currentPageName==null){
            currentPageName=""+request.getRequestURL();
        }
        System.out.println("ServletComboListener.requestInitialized:");
        System.out.println("  A request has been created "+currentPageName);
    }
    
}
