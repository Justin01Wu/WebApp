package wu.justin.rest2.exception;

import javax.ws.rs.core.Response;

public class StatusMessageException extends Exception {

	private static final long serialVersionUID = 12453L;
	
	private String message;
	private String devMess;
	private Response.Status status;
	
	public StatusMessageException(String message, String devMess, Response.Status status) {
		this.message = message;
		this.devMess = devMess;
		this.status = status;		
	}	
	public StatusMessageException(String message, Response.Status status) {
		this(message, null, status);		
	}
	public StatusMessageException(String message, Exception e) {		
		this("API internal processing error.", e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		//log.error(message, e);
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDevMess() {
		return devMess;
	}

	public void setDevMess(String devMess) {
		this.devMess = devMess;
	}

	public Response.Status getStatus() {
		return status;
	}

	public void setStatus(Response.Status status) {
		this.status = status;
	}
}