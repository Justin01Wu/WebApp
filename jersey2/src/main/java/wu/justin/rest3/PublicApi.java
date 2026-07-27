package wu.justin.rest3;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import wu.justin.bean.DateConvert;
import wu.justin.bean.MyTimestamp;
import wu.justin.bean.Student;
import wu.justin.rest2.MySetting;
import wu.justin.rest2.exception.StatusMessageException;

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
	@Path("/dbIp")
	public String getDbIp() {
		
		//String value = MySetting.DbIp.getValue();
		String value = MySetting.DbIp.getValueWithReload();
		
		if(value == null || value.isEmpty()){
			System.out.println("can't find db ip...");
			throw new RuntimeException("can't find db ip...");
		}

	    return "{\"dbIp\":\""+ value + "\"}";
	}
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dateFormat.json")
	public DateConvert getDateFormat(@Context HttpServletRequest httpRequest) {		
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
		result.add(3435);
		result.add(4567);
		
	    return result;
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/timestamp.json")
	public MyTimestamp postTimestamp(MyTimestamp dateConvert) {		
	    return dateConvert;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/timestamp.json")
	public MyTimestamp getTimestamp() {		
		MyTimestamp result =new MyTimestamp();
		result.setRunDate(new Timestamp(System.currentTimeMillis()));
	    return result;
	}

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/error/errorTest.json")
	public String getStatusMessageException() throws StatusMessageException {
		
		throw new StatusMessageException("test StatusMessageException 435345", Response.Status.NOT_ACCEPTABLE);
	    
	}

	


	
}

