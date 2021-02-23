package wu.justin.rest2.exception;

import java.security.InvalidParameterException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidParameterExceptionMapper implements ExceptionMapper<InvalidParameterException> {
	
	
	@Override
	public Response toResponse(InvalidParameterException ex) {
		
		System.err.println(ex.getMessage());

		return Response.status(Status.BAD_REQUEST)
					.entity("IP:"  + ex.getMessage())
					.type(MediaType.TEXT_PLAIN).
					build();
		
	}



}
