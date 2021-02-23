package wu.justin.rest2.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
