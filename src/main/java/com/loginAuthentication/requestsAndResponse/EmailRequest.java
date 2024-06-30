package com.loginAuthentication.requestsAndResponse;

public class EmailRequest {
	
	private String email;

	public EmailRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailRequest(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailRequest [email=" + email + "]";
	}
	
	
	
}
