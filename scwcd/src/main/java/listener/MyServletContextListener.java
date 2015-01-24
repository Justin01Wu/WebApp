package listener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
public class MyServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce){
        System.out.println("MyServletContextListener.contextInitialized");
    }

    //Notification that a session was invalidated
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("MyServletContextListener.contextDestroyed");
    }
}
