package wu.justin.bean;

public class User2 {
	private ObjectId id;
    private String username;
    private String password;
    
    public User2(){
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	} 

}