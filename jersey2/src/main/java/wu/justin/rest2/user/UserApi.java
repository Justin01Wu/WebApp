package wu.justin.rest2.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import wu.justin.bean.IdText;
import wu.justin.bean.Pair2;
import wu.justin.bean.User;
import wu.justin.rest2.exception.BadRequestError;

/**
 * This is APIs for user related API
 * @author justin.wu
 *
 */
@Path("/users")
public class UserApi {
	
	
	
	/** demo how to convert java object tree to json */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/current.json")	
	public User getCurrentUser(@Context User user) {
		
		System.out.println("getCurrentUser...");
		
		return user;
	}
	
	public User iAmNotApi(@Context HttpServletRequest httpRequest, @PathParam("userId") Integer userId) {
		
		System.out.println("getUserById...");
		
		User user = UserService.getUserById(userId);
		return user;
	}
	
	
	/** demo how to use PathParam  */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userId}")	
	public User getUserById(@Context HttpServletRequest httpRequest, @PathParam("userId") Integer userId) {
		
		System.out.println("getUserById...");
		if(userId<=0){
			throw new BadRequestError("userId can't be negative");
		}
		User user = UserService.getUserById(userId);
		return user;
	}
	
	/** this one has exact function as /user/{userId} except its userId is int rather than Integer*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user2/{userId2}")	
	public User getUserById2(@Context HttpServletRequest httpRequest, @PathParam("userId2") int userId) {
		
		System.out.println("getUserById...");
		if(userId<=0){
			throw new BadRequestError("userId can't be negative");
		}
		User user = UserService.getUserById(userId);
		return user;
	}
	
	/** demo how to use PathParam  */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/range.json")	
	public List<User> getUserByRange(		
			@QueryParam("from") int from,
			@QueryParam("to") int to,
			@QueryParam("orderBy") List<String> orderBy)  {
		
		System.out.println("getUserByRange...");
		
		Pair2<Integer, Integer> range = new Pair2<>(from, to);
		
		return UserService.getUserByRange(range);
	}
	
	/** demo how to use PathParam  */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users.json")	
	public List<IdText> getUsers(		
			@QueryParam("from") int from,
			@QueryParam("to") int to)  {
		
		System.out.println("getUserByRange...");
		
		Pair2<Integer, Integer> range = new Pair2<>(from, to);
		List<User> users = UserService.getUserByRange(range);
		List<IdText> result = new ArrayList<>();
		for(User u :users) {
			
			IdText vo = new IdText();
			vo.setId(u.getId());
			vo.setText(u.getName());
			result.add(vo);
			
		}
		return result;
	}
	
	/** demo how to convert java object tree to json 
	 * @throws IOException */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/user/current.json")
	public User setCurrentUser(User userDTO) throws IOException {
		
		System.out.println("setCurrentUser...");
		
		User currentUser = UserService.getUserById(56239);
		
		
		// use userDTO to override currentUser to avoid partial json return because @JsonIgnore will ignore some data
		// for detail, please see http://stackoverflow.com/questions/12518618/deserialize-json-into-existing-object-java
		// or http://stackoverflow.com/questions/9895041/merging-two-json-documents-using-jackson
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectReader updater = objectMapper.readerForUpdating(currentUser);
		String overridesJson = objectMapper.writeValueAsString(userDTO);		
		User merged = updater.readValue(overridesJson);
		
		
	    return merged;
	}
	
	/** demo how to generate 403 error 
	  */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/user/current.json")
	public String createCurrentUser(User userDTO, @QueryParam("testFlag") Boolean testFlag) {
		
		if(testFlag!= null && testFlag.equals(true)){
			return "{\"msg\": \"it is test request, so do nothing\"}";
		}
		if(userDTO == null) {
			throw new ForbiddenException("test 403 error");	
		}
		UserService.addUser(userDTO);
		return "{\"msg\": \"succeed\"}";
		
	}
	

}
