package jmx;

import java.io.IOException;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


// orig comes from 
//   http://stackoverflow.com/questions/1751130/calling-jmx-mbean-method-from-a-shell-script


public class CallJmx {
	public static void main(String[] args) {
		try {

			JMXServiceURL url = new JMXServiceURL(
					"service:jmx:rmi:///jndi/rmi://localhost:9087/jmxrmi");
			// this port 9087 is defined in your start.bat of tomcat:
			// set CATALINA_OPTS=-Dcom.sun.management.jmxremote 
			//     -Dcom.sun.management.jmxremote.ssl=false 
			//     -Dcom.sun.management.jmxremote.port=9087 
			//     -Dcom.sun.management.jmxremote.authenticate=false

			JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			getRootLogLevel(mbsc);
			
			getClassLogLevel(mbsc, "bm.validusre.vcaps.cmc.service.AttachedDatabaseDao");
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

	private static void getClassLogLevel( MBeanServerConnection mbsc, String classfullPath ) throws MalformedObjectNameException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException{
		ObjectName objectName = new ObjectName("Welcome to VRe:type=VcapsLog4J");

        //  Create operation's parameter and signature arrays
        
        Object[]  opParams = { classfullPath  };
        
        String[]  opSig = { String.class.getName() };
    
    //  Invoke operation
        String msg  =	(String)mbsc.invoke(objectName, "getClassOrPackageLevel", opParams, opSig);
        
		System.out.println(classfullPath + " log level = " + msg);		
	}

	
	
	
	private static void getRootLogLevel( MBeanServerConnection mbsc) throws MalformedObjectNameException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException, IOException{
		ObjectName log4jBean = new ObjectName("Welcome to VRe:logger=root");

		String logRoot = mbsc.getAttribute(log4jBean, "priority")
				.toString();
		System.out.println("log4j root log level = " + logRoot);

		
	}
}
