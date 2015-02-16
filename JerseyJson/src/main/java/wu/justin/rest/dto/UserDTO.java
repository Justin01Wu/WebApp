package wu.justin.rest.dto;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import wu.justin.business.User;

public class UserDTO  extends User {
	
	public UserDTO(Integer id, String name){
		super(id, name);
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@JsonProperty("fullName")
	@Override
	public String getName() {
		return super.getName();
	}
	
//	@JsonProperty("fullName")
//	@Override
//	public void setName(String name) {
//		this.setName(name);
//	}	

}
