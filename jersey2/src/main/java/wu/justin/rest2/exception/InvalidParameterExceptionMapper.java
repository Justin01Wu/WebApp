package wu.justin.rest2.exception;

import java.security.InvalidParameterException;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

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
