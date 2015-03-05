package wu.justin.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import wu.justin.rest.exception.BadRequestException;

// it comes from 
// http://www.mkyong.com/webservices/jax-rs/jersey-hello-world-example/

@Path("/hello")
public class HelloWorldService {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;

		return Response.status(200).entity(output).build();

	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("/xml")
	public String sayXMLHello() {
	    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/json")
	public String sayJsonHello() {
	    return "{\"msg\":\"Hello Jersey\"}";
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/error")
	public String sayJsonHello2 () throws BadRequestException {
	    throw new BadRequestException("this is my error message 12456745");
	}

	

}