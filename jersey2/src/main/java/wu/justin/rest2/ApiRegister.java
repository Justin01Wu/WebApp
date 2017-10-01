package wu.justin.rest2;

import org.glassfish.jersey.server.ResourceConfig;

import wu.justin.rest2.user.UserApi;

public class ApiRegister extends ResourceConfig {
	
	public ApiRegister() {
		register(PublicApi.class);
		register(UserApi.class);
		register(JacksonObjectMapperProvider.class);
	}

}
