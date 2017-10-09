package wu.justin.rest2.exception;

import javax.ws.rs.WebApplicationException;

public class BadRequestError extends  WebApplicationException {

	private static final long serialVersionUID = 235435431L;
	private String mediaType;
	
	public BadRequestError(String message) {
		super(message);
	}
	
	public BadRequestError(String message, String type){
		super(message);
		this.mediaType = type;
	}
	
	public String getMediaType(){
		return this.mediaType;
	}

}