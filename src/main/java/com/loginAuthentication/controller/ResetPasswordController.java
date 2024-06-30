package com.loginAuthentication.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginAuthentication.Entity.Otp;
import com.loginAuthentication.Entity.User;
import com.loginAuthentication.requestsAndResponse.EmailRequest;
import com.loginAuthentication.requestsAndResponse.PasswordSetRequest;
import com.loginAuthentication.requestsAndResponse.VerifyOtpRequest;
import com.loginAuthentication.service.OtpService;
import com.loginAuthentication.service.PasswordRecoveryMailService;
import com.loginAuthentication.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/reset-password")
public class ResetPasswordController {
	
	@Autowired
	private PasswordRecoveryMailService passwordRecoveryMailService;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired 
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetPasswordHandler(@RequestBody EmailRequest emailRequest) {
		System.out.println(emailRequest);
		this.passwordRecoveryMailService.sendEmail(emailRequest.getEmail());
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<?> verifyPasswordRecoveryOtp(@RequestBody VerifyOtpRequest  verifyOtpRequest) {
		
		System.out.println(verifyOtpRequest);
		
		User user = this.userDetailsServiceImpl.loadUserByEmail(verifyOtpRequest.getEmail());
		Otp otp = this.otpService.getOtp(verifyOtpRequest.getOtpVal());
		
		if(user.getEmail() != otp.getUser().getEmail() || Instant.now().compareTo(otp.getExpirationTime()) > 0) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The otp verification failed!!");
		}
		return ResponseEntity.ok(HttpStatus.OK);
		
	}
	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordSetRequest passwordSetRequest) {
		
		User user = this.userDetailsServiceImpl.loadUserByEmail(passwordSetRequest.getEmail());
		
		user.setPassword(passwordEncoder.encode(passwordSetRequest.getNewPassword()));
		
		userDetailsServiceImpl.saveUser(user);
		
		return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully !!");
		
		
	}
	
}
