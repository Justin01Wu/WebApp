package wu.justin.rest2;

import org.glassfish.jersey.server.ResourceConfig;

public class ApiRegister extends ResourceConfig {
	
	public ApiRegister() {
		register(PublicApi.class);
		//register(JacksonObjectMapperProvider.class);
	}

}
