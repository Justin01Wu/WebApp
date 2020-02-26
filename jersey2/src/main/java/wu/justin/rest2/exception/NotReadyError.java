package wu.justin.rest2.exception;

import javax.ws.rs.WebApplicationException;

public class NotReadyError extends  WebApplicationException {

	private static final long serialVersionUID = 23543543321L;
	
	public NotReadyError(String message) {
		super(message);
	}

}
