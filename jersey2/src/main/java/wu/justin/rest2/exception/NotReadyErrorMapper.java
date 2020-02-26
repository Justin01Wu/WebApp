package wu.justin.rest2.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class NotReadyErrorMapper implements ExceptionMapper<NotReadyError> {
	
	//private static Logger log = Logger.getLogger(NotReayErrorMapper.class);
	
	@Override
	public Response toResponse(NotReadyError ex) {
		
		//log.info(ex.getMessage());
		
        //  https://stackoverflow.com/questions/9794696/how-do-i-choose-a-http-status-code-in-rest-api-for-not-ready-yet-try-again-lat
		
		return Response.status(Status.ACCEPTED).entity(ex.getMessage())
				.type(MediaType.TEXT_PLAIN).build();
		
	}



}