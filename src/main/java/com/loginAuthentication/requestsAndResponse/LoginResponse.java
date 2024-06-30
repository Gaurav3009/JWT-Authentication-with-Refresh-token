package com.loginAuthentication.requestsAndResponse;

public class LoginResponse {
	
	private String username;
	private String token;
	private String refreshToken;
	
	public LoginResponse(String username, String token, String refreshToken) {
		super();
		this.username = username;
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "LoginResponse [username=" + username + ", token=" + token + ", refreshToken=" + refreshToken + "]";
	}
	
	
	
}
