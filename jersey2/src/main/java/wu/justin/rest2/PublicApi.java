package wu.justin.rest2;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import wu.justin.bean.DateConvert;
import wu.justin.bean.MyTimestamp;
import wu.justin.bean.Student;

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
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/objectId.json")
	public Student getStudent() {
		
		Student student =  new Student();
	    return student;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/int/list.json")
	public List<Integer> getIntegerList() {
		
		List<Integer> result = new ArrayList<>();
		result.add(34345);
		
	    return result;
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/timestamp.json")
	public MyTimestamp postTimestamp(MyTimestamp dateConvert) {		
	    return dateConvert;
	}


	
}

