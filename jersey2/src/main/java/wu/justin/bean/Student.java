package wu.justin.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * test toJsonString method on ObjectId
 */
public class Student {
	private ObjectId id;
    private String username;
    private String password;
    
    public Student(){
    	this.id = new ObjectId();
    	this.username="justin";
    	this.password="password";
    }
    
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	} 

}
