package wu.justin.rest.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class User {
	
	private Integer id;	
	private String name;
	
	@JsonIgnore
	private String password;
	
	private List<String> emails = new ArrayList<String>();
	
	
	
	private Address homeAddress;
	
	public User(){		
	}
	public User(Integer id, String name){
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emailList) {
		this.emails = emailList;
	}
	public void addEmails(String email) {
		this.emails.add(email);
	}
	
	public Address getHomeAddress() {
		return homeAddress;
	}
	
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
