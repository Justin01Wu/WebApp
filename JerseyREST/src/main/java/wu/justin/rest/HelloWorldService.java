package wu.justin.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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

}