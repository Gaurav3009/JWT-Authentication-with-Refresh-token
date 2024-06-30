package com.loginAuthentication.requestsAndResponse;

public class PasswordSetRequest {
	
	private String newPassword;
	private String email;
	
	public PasswordSetRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordSetRequest(String newPassword, String email) {
		super();
		this.newPassword = newPassword;
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "PasswordSetRequest [newPassword=" + newPassword + ", email=" + email + "]";
	}
	
	
	
}
