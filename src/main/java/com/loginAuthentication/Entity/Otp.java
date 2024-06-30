package com.loginAuthentication.Entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OTP")
public class Otp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int otp;
	private Instant expirationTime;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User user;
	
	public Otp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Otp(int otp, User user, Instant expirationTime) {
		super();
		this.otp = otp;
		this.user = user;
		this.expirationTime = expirationTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Instant getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Instant expirationTime) {
	this.expirationTime = expirationTime;
	}

	@Override
	public String toString() {
		return "Otp [id=" + id + ", otp=" + otp + ", user=" + user + ", expirationTime=" + expirationTime + "]";
	}
	
	
	
}
