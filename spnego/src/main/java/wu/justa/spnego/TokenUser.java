package wu.justa.spnego;

import java.util.Date;

public class TokenUser {
	
	private String userName;
	private String realUserName;
	private String email;
	private Date tokenCreateTime;
	private Date tokenExpiredTime;
	private String tokenKey;
	
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
	public String getRealUserName() {
		return realUserName;
	}
	public void setRealUserName(String realUserName) {
		this.realUserName = realUserName;
	}

}
