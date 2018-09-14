package wu.justin.rest2.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
	
	
	@Override
	public Response toResponse(IllegalArgumentException ex) {
		
		System.err.println(ex.getMessage());

		return Response.status(Status.BAD_REQUEST)
					.entity("IA:"  + ex.getMessage())
					.type(MediaType.TEXT_PLAIN).
					build();
		
	}



}
