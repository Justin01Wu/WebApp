package wu.justin.rest2.exception;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;


// please also see https://stackoverflow.com/questions/41301386/jax-rs-jersey-exceptionmapper-how-to-know-the-method-who-threw-the-exception/43610403
// please also see https://stackoverflow.com/questions/42263467/get-produces-annotation-of-method-that-threw-exception-in-exceptionmapper

@Provider
public class StatusMessageExceptionMapper implements ExceptionMapper<StatusMessageException> {	
	
	private static Logger log = Logger.getLogger(StatusMessageExceptionMapper.class);
	
	@Context private ResourceInfo resourceInfo;
	@Context private HttpServletRequest request;
	
	@Override
	public Response toResponse(StatusMessageException ex) {		
		
		Class<?> cls = resourceInfo.getResourceClass();
		Method method = resourceInfo.getResourceMethod();		

		log.info( cls.getName() + "."+ method.getName() + ": " + ex.getMessage());
		log.info( ex.getStatus() + ": " + request.getMethod() + ": " + request.getRequestURI()  );
		
		ApiError appError = new ApiError();
		appError.setMessage(ex.getMessage());
		appError.setDevMessage(ex.getDevMess());
		appError.setStatus(ex.getStatus().getStatusCode());
		String mediaType = getMediaTypeFromResponse(cls, method).get(0);
		
			
		return Response.status(ex.getStatus())
					.entity(appError)
					.type(mediaType).
					build();
		
	}
	
	private static List<String> getMediaTypeFromResponse(Class<?> cls, Method method) {

		List<String> mediaTypes = new ArrayList<>();
		Produces produces = method.getAnnotation(Produces.class);
		if (produces == null) {
		    produces = cls.getAnnotation(Produces.class);
		}
		if (produces != null) {
			mediaTypes = Arrays.asList(produces.value());
		}
		return mediaTypes;
	}



}
