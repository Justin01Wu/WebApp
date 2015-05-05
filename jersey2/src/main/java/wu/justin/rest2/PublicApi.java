package wu.justin.rest2;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import wu.justin.bean.Address;
import wu.justin.bean.User;

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
