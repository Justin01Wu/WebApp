package wu.justin.rest2;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

import wu.justa.service.UserContextProvider;
import wu.justin.bean.User;

// from https://stackoverflow.com/questions/32119962/jersey-custom-context-injection
@ApplicationPath("/webresources")
public class ApiConfig extends ResourceConfig {

	    public ApiConfig() {
	        
	        packages("wu.justin.rest2;wu.justin.rest3");
	        
	        register(new AbstractBinder(){
	            @Override
	            protected void configure() {
	                bindFactory(UserContextProvider.class)
	                        .to(User.class)
	                        .in(RequestScoped.class);
	            }
	        });
	   }

}
