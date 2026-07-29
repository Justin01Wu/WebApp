package wu.justin.rest2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;
import wu.justa.proxy.InnerUser;
import wu.justa.service.UserContextProvider;

// from https://stackoverflow.com/questions/32119962/jersey-custom-context-injection
@ApplicationPath("/")
// Keep API paths rooted at servlet mapping (/apiReal/*) so proxy /api/* -> /apiReal/* stays valid.
public class ApiConfig extends ResourceConfig {

	    public ApiConfig() {
	        
	    	// tell API frame which packages can scan API implementation
	        packages("wu.justin.rest2;wu.justin.rest3");
	        
	        register(new AbstractBinder(){
	            @Override
	            protected void configure() {
	                bindFactory(UserContextProvider.class)
	                        .to(InnerUser.class)
	                        .in(RequestScoped.class);
	            }
	        });
	   }

}