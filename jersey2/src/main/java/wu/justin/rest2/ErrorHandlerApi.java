package wu.justin.rest2;

import java.security.InvalidParameterException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import wu.justin.bean.DateConvert;

/**
 * those api show up how to handle a exception
 * @author justin.wu
 *
 */

@Path("/errors")
public class ErrorHandlerApi {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/rsError.json")
	public String triggerRsError() {		
	    throw new BadRequestException(" test BadRequestException");
	}
	
	
	/**
	 * Justin test comment
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ipMapper.json")
	public DateConvert triggerIPException() {		
	    throw new InvalidParameterException(" test InvalidParameterException");
	    
	 // even InvalidParameterException is subclass of IllegalArgumentException, Jersey still use most accurate mapper to handle exception
	    // i.e. it will use InvalidParameterExceptionMapper rather than IllegalArgumentExceptionMapper, no matter the order they register
	}

	/**
	 * Justin test block comment
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/iaMapper.json")
	public DateConvert triggerIAException() {		
	    throw new IllegalArgumentException(" test IllegalArgumentException");
	    
	     
	}
	

}
