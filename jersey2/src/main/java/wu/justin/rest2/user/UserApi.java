package wu.justin.rest2.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import wu.justin.bean.Address;
import wu.justin.bean.User;
import wu.justin.rest2.MySetting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

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
	public User getCurrentUser() {
		
		System.out.println("getCurrentUser...");
		
		String value = MySetting.DbIp.getValue();
		if(value == null || value.isEmpty()){
			System.out.println("can't find db ip...");
			throw new RuntimeException("can't find db ip...");
		}
		
		User user = createUser();
		return user;
	}
	
	/** demo how to use PathParam  */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userId}")	
	public User getUserById(@Context HttpServletRequest httpRequest, @PathParam("userId") Integer userId) {
		
		System.out.println("getUserById...");
		
		User user = createUser();
		return user;
	}
	
	/** demo how to use PathParam  */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/range.json")	
	public User getUserByRange(		
			@QueryParam("from") int from,
			@QueryParam("to") int to,
			@QueryParam("orderBy") List<String> orderBy)  {
		
		System.out.println("getUserById...");
		
		User user = createUser();
		return user;
	}
	
	/** demo how to convert java object tree to json 
	 * @throws IOException */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/user/current.json")
	public User setCurrentUser(User userDTO) throws IOException {
		
		System.out.println("setCurrentUser...");
		
		User currentUser = createUser();
		
		
		// use userDTO to override currentUser to avoid partial json return because @JsonIgnore will ignore some data
		// for detail, please see http://stackoverflow.com/questions/12518618/deserialize-json-into-existing-object-java
		// or http://stackoverflow.com/questions/9895041/merging-two-json-documents-using-jackson
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectReader updater = objectMapper.readerForUpdating(currentUser);
		String overridesJson = objectMapper.writeValueAsString(userDTO);		
		User merged = updater.readValue(overridesJson);
		
		
	    return merged;
	}
	
	private static User createUser(){
		User user = new User(56239, "Justin Wu");
		user.addEmails("justin01.wu@gmail.com");
		user.addEmails("wuyg719@gmail.com");
		user.setBirthDate(new Date());
		
		user.setPassword("abcd1234");
		
		Address homeAddress =  new Address();
		homeAddress.setId(123768);
		homeAddress.setCountry("Canada");
		homeAddress.setAddress("This is a tab character[	],but jackson will convert it into \t");
		
		user.setHomeAddress(homeAddress);
		
		user.setDesc("This is a sample to demo how Jackson convert a Java object tree into json");
	    return user;
		
	}
}
