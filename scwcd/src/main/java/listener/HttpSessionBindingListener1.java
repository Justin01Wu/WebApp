package listener;

import javax.servlet.http.*;

public class HttpSessionBindingListener1 implements HttpSessionBindingListener {

    int userId;
    String userName;
    String password;

    public HttpSessionBindingListener1() {
        System.out.println("create HttpSessionBindingListener1");
    }

    /** if this object is bound to a session, then the method is called.
    The servlet container calls the interface methods even if the session 
     * is explicitly invalidated or has timed out.
    The valueBound() method is called BEFORE the object becomes accessible 
     * through HttpSession.getAttribute()
     */
    public void valueBound(HttpSessionBindingEvent event) {
        //HttpSessionBindingEvent is also applied methods of HttpSessionAttributerListener1
        HttpSession session = event.getSession();
        HttpSessionBindingListener1 a = (HttpSessionBindingListener1) (session.getAttribute("user"));
        System.out.println("");
        System.out.println("HttpSessionBindingListener1.valueBound:");
        if (a != null) {
            System.out.println("  user has been binded");
        }
        
        System.out.println("  This object is going to be binded to: " + session.getId());
    }

    /**The valueUnbound() method is called AFTER the object is removed from the HttpSession.*/
    public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        System.out.println("");
        System.out.println("HttpSessionBindingListener1.valueUnbound:");
        System.out.println("  this object has been removeded from " + session.getId());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
