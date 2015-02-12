package wu.justin.filter;

import java.util.Hashtable;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class RegisterMBeanServlet extends HttpServlet {
		
		private static final long serialVersionUID = 1242346545751L;

		public void init(ServletConfig servletConfig) throws ServletException {
			
			System.out.println("going to register JMXBean ..." );
			ServletContext context = servletConfig.getServletContext();
			
			String webappName = context.getServletContextName();
			System.out.println("webappName = " + webappName );
			

			Controller mBean = Controller.getInstance();        
			
			System.out.println("going to get MBeanServer ..." );
			
			List<?> list = MBeanServerFactory.findMBeanServer(null);
			MBeanServer server = (MBeanServer) list.iterator().next();

	        //register the MBean
			try {
				// using the Web application name as domain to avoid name conflict 
				
				Hashtable<String, String> nameParameters = new Hashtable<String, String>();
				nameParameters.put("type", "ThrottlingController");

				ObjectName ObjectName = new ObjectName(webappName, nameParameters);		
				if(server.isRegistered(ObjectName)){
					System.out.println("unregister old ThrottlingController...");
					server.unregisterMBean(ObjectName);
				}
				server.registerMBean(mBean, ObjectName);
				System.out.println("registered VcapsLog4J under " + webappName);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

