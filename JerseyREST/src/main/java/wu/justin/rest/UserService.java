package wu.justin.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import wu.justin.rest.dto.Address;
import wu.justin.rest.dto.User;

//it comes from 
// http://www.vogella.com/tutorials/REST/article.html

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
		
		User user = new User(12347, "Justin Wu");
		user.addEmails("justin01.wu@gmail.com");
		user.addEmails("wuyg719@gmail.com");
		
		Address homeAddress =  new Address();
		homeAddress.setId(123768);
		homeAddress.setCountry("Canada");
		homeAddress.setAddress("2244 munn's ave");
		
		user.setHomeAddress(homeAddress);
	    return user;
	}
	
}