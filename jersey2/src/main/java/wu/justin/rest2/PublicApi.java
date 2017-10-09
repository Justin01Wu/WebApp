package wu.justin.rest2;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import wu.justin.bean.DateConvert;
import wu.justin.bean.User2;

/**
 * This is APIs for public access, anybody can access them
 * @author justin.wu
 *
 */
@Path("/public")
public class PublicApi {
	
	/**
	 * find if server is available 
	 * @return {}
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ping")
	public String ping() {		
	    return "{}";
	}	

	/**
	 * get server time on long format
	 * @return server time on long format
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/time")
	public String time() {
		long time = System.currentTimeMillis();
	    return "{\"time\":"+ time + "}";
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dateFormat.json")
	public DateConvert getDateFormat() {		
	    return new DateConvert();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/dateFormat.json")
	public DateConvert postDateFormat(DateConvert dateConvert) {		
	    return dateConvert;
	}
	/**
	 * test toJsonString method
	 * @deprecated, please use /users/user/current.json
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/objectId.json")
	public User2 getUser2() {
		
		User2 user2 =  new User2();
	    return user2;
	}


}

