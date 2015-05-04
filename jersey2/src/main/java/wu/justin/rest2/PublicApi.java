package wu.justin.rest2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/public")
public class PublicApi {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ping")
	public String ping() {		
	    return "{}";
	}	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/time")
	public String time() {
		long time = System.currentTimeMillis();
	    return "{\"time\":"+ time + "}";
	}

}
