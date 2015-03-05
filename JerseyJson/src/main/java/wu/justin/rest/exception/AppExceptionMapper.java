package wu.justin.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

// comes from http://www.codingpedia.org/ama/error-handling-in-rest-api-with-jersey/
// and http://www.codingpedia.org/ama/custom-reason-phrase-in-http-status-error-message-response-with-jax-rs-jersey/

@Provider
public class AppExceptionMapper implements ExceptionMapper<BadRequestException> {

	public Response toResponse(BadRequestException ex) {
		return Response.status(400)
				.entity(ex.getMessage())
				.type(MediaType.TEXT_PLAIN).
				build();
	}



}
