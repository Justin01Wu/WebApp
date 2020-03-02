package wu.justin.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import wu.justin.rest2.converter.ClassRoomSerializer;
import wu.justin.rest2.converter.JsonTargetEnum;
import wu.justin.rest2.converter.JsonTargetType;

/**
 * test toJsonString method on ObjectId
 */
public class Student {
	private ObjectId id;
	private Integer classRoomId;  // foreign key
    private String username;
    private String password;
    
    public Student(){
    	this.id = new ObjectId();
    	this.username="justin";
    	this.password="password";
    	this.classRoomId=2;
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

	@JsonProperty("classroom")
	@JsonSerialize(using = ClassRoomSerializer.class)
	public Integer getClassRoomId() {
		return classRoomId;
	}	
	
	public void setClassRoomId(Integer classRoomId) {
		this.classRoomId = classRoomId;
	} 
	
	@JsonProperty("classroom2")
	@JsonSerialize(using = ClassRoomSerializer.class)
	@JsonTargetType(getType = JsonTargetEnum.Desc)   // the difference from getClassRoomId
	// from https://stackoverflow.com/questions/22634860/how-to-pass-parameter-to-jsonserializer
	public Integer getClassRoomId2() {
		return classRoomId;
	}	

}
