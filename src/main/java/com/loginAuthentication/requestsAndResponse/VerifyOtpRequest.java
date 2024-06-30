package com.loginAuthentication.requestsAndResponse;

public class VerifyOtpRequest {
	
	private String email;
	private int otpVal;
	
	public VerifyOtpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VerifyOtpRequest(String email, int otpVal) {
		super();
		this.email = email;
		this.otpVal = otpVal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getOtpVal() {
		return otpVal;
	}

	public void setOtpVal(int otpVal) {
		this.otpVal = otpVal;
	}

	@Override
	public String toString() {
		return "VerifyOtpRequest [email=" + email + ", otpVal=" + otpVal + "]";
	}
	
	
}
