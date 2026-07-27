package wu.justin.rest2.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestErrorMapper implements ExceptionMapper<BadRequestError> {
	
	
	@Override
	public Response toResponse(BadRequestError ex) {
		
		System.err.println(ex.getMessage());

		return Response.status(Status.BAD_REQUEST)
					.entity(ex.getMessage())
					.type(MediaType.TEXT_PLAIN).
					build();
		
	}



}
