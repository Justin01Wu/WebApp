package wu.justin.rest2;

import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import wu.justa.service.UserContextProvider;
import wu.justin.bean.User;

// from https://stackoverflow.com/questions/32119962/jersey-custom-context-injection
@ApplicationPath("/webresources")
public class ApiConfig extends ResourceConfig {

	    public ApiConfig() {
	        
	    	// tell API frame which packages can scan API implementation
	        packages("wu.justin.rest2;wu.justin.rest3");
	        
	        // tell swagger to generate openapi.json under [apiRoot]
	        register(OpenApiResource.class);
	        
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
