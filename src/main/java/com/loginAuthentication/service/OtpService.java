package com.loginAuthentication.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.loginAuthentication.Dao.OtpRepository;
import com.loginAuthentication.Entity.Otp;
import com.loginAuthentication.Entity.User;

@Service
public class OtpService {
	
	@Autowired
	private OtpRepository otpRepository;
	
	public void saveOtp(Otp otp) {
		this.otpRepository.save(otp);
	}
	
	public Otp getOtp(int otpVal) {
//		Otp otp = this.otpRepository.findOtpByUserId(user.getUserId());
		Otp otp = this.otpRepository.findOtpByOtp(otpVal);
		return otp;
	}
	
	@Scheduled(fixedDelay = 3600000)
	public void removeExpiredOtp() {
		Instant current = Instant.now();
		List<Otp> expiredOtps = this.otpRepository.findAllByExpirationTimeBefore(current);
		this.otpRepository.deleteAll(expiredOtps);
	}
	
}
