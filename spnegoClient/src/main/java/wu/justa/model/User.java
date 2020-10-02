package wu.justa.model;

import java.util.Date;

public class User {
	
	private String userName;
	private String email;
	private Date tokenCreateTime;
	private Date tokenExpiredTime;
	private String token;
	private String tokenKey;
	private String status ="active";
	private String domainUserName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getTokenCreateTime() {
		return tokenCreateTime;
	}
	public void setTokenCreateTime(Date tokenCreateTime) {
		this.tokenCreateTime = tokenCreateTime;
	}
	public Date getTokenExpiredTime() {
		return tokenExpiredTime;
	}
	public void setTokenExpiredTime(Date tokenExpiredTime) {
		this.tokenExpiredTime = tokenExpiredTime;
	}
	public String getTokenKey() {
		return tokenKey;
	}
	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}
	public String getDomainUserName() {
		return domainUserName;
	}
	public void setDomainUserName(String domainUserName) {
		this.domainUserName = domainUserName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
