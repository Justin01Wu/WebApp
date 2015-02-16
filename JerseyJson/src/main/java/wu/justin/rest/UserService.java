package wu.justin.rest;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;

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
	
	/** demo how to convert java object tree to json */
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
		
		user.setDesc("This is a sample to demo how Jackson convert a Java object tree into json");
	    return user;
	}
	
	/** demo how to hide password */
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
		user.setDesc("This is a sample to hide password and set special name on json when convert a Java object tree into json");
	    return user;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/current/menu")
	public String getUserMenu() throws IOException {

		URL url = UserService.class.getResource("/../menu.json");
		File menuFile = new File(url.getPath());	
		String menuStr = FileUtils.readFileToString(menuFile);
		return menuStr;
	}
}