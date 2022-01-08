package org.psu.dUmasankar.LMS;

public class UserSession {
	
	private String username;
	private String role;
	
	public UserSession(String username, String role) {
		this.username = username;
		this.role = role;
	}
	
	public UserSession() {
		this.username = "Guest";
		this.role = "Guest";
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getRole() {
		return role;
	}
	
	public void cleanUserSession() {
		username = "";
		role = "";
	}
}
