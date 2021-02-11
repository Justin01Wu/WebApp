package wu.justin.rest2;

import org.glassfish.jersey.server.ResourceConfig;

import wu.justin.rest2.exception.BadRequestErrorMapper;
import wu.justin.rest2.exception.IllegalArgumentExceptionMapper;
import wu.justin.rest2.exception.InvalidParameterExceptionMapper;
import wu.justin.rest2.exception.NotReadyErrorMapper;
import wu.justin.rest2.exception.StatusMessageExceptionMapper;
import wu.justin.rest2.user.UserApi;

/** @deprecated, replaced with packages setting in web.xml, will be deleted soon*/
@Deprecated
public class ApiRegister extends ResourceConfig {
	
	public ApiRegister() {
		
		// exception mapper
		register(BadRequestErrorMapper.class);	
		register(NotReadyErrorMapper.class);		
		
		register(InvalidParameterExceptionMapper.class);
		register(IllegalArgumentExceptionMapper.class);		
		register(StatusMessageExceptionMapper.class);		
				
		register(PublicApi.class);
		register(UserApi.class);
		register(ErrorHandlerApi.class);		
		register(JacksonObjectMapperProvider.class);
	}

}
