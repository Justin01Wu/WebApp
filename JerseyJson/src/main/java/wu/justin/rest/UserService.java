package wu.justin.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.util.BeanUtil;

import wu.justin.business.User;
import wu.justin.rest.dto.Address;
import wu.justin.rest.dto.UserDTO;

//it comes from 
// http://www.mkyong.com/webservices/jax-rs/json-example-with-jersey-jackson/

@Path("/user")
public class UserService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/json")
	public String sayJsonHello() {
	    return "{\"user\":\"Justin Wu\"}";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/current")
	public User getCurrentUser() {
		
		User user = new User(56239, "Justin Wu");
		user.addEmails("justin01.wu@gmail.com");
		user.addEmails("wuyg719@gmail.com");
		
		user.setPassword("abcd1234");
		
		Address homeAddress =  new Address();
		homeAddress.setId(123768);
		homeAddress.setCountry("Canada");
		homeAddress.setAddress("2244 munn's ave");
		
		user.setHomeAddress(homeAddress);
	    return user;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/currentNoPassword")
	public UserDTO getCurrentUser2() {
		
		UserDTO user = new UserDTO(77349, "Justin Wu");
		user.addEmails("justin01.wu@gmail.com");
		user.addEmails("wuyg719@gmail.com");
		
		user.setPassword("abcd1234");
		
		Address homeAddress =  new Address();
		homeAddress.setId(123768);
		homeAddress.setCountry("Canada");
		homeAddress.setAddress("273 sixteen");
		
		user.setHomeAddress(homeAddress);

	    return user;
	}
	
}