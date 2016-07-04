package wu.justin.rest2;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import wu.justin.bean.Address;
import wu.justin.bean.DateConvert;
import wu.justin.bean.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

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
	
	/** demo how to convert java object tree to json */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public User getCurrentUser() {
		
		System.out.println("getCurrentUser...");
		
		User user = createUser();
		return user;
	}
	
	/** demo how to convert java object tree to json 
	 * @throws IOException */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/user")
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dateFormat")
	public DateConvert dateFormat() {		
	    return new DateConvert();
	}

}
