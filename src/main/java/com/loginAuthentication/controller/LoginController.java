package com.loginAuthentication.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loginAuthentication.Entity.RefreshToken;
import com.loginAuthentication.Entity.User;
import com.loginAuthentication.JWT.JwtUtils;
import com.loginAuthentication.requestsAndResponse.LoginRequest;
import com.loginAuthentication.requestsAndResponse.LoginResponse;
import com.loginAuthentication.requestsAndResponse.RefreshTokenRequest;
import com.loginAuthentication.service.PasswordRecoveryMailService;
import com.loginAuthentication.service.RefreshTokenService;
import com.loginAuthentication.service.UserDetailsServiceImpl;


@RestController
public class LoginController {
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public String test() {
		return "The login api is working";
	}
	
	@PostMapping("/register")
	public ResponseEntity registerHandler(@RequestBody User user) {
		user.setRole("USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userDetailsServiceImpl.saveUser(user);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshTokenHandler(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		
		String token = refreshTokenRequest.getToken();
		RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken()).get();
		
		System.out.println(refreshTokenRequest.getToken());
		
		try {
			refreshToken = refreshTokenService.verifyExpiration(refreshToken);
			refreshToken.getExpiryDate().plusMillis(24 * 60 * 60 * 1000);
		}catch(Exception e) {
			Map<String, Object> map = new HashMap<>();
			map.put("message", "Refresh token expired");
			map.put("status", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
			
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		}
		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(refreshToken.getUser().getUsername());
		String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
		
		LoginResponse response = new LoginResponse(userDetails.getUsername(), jwtToken, refreshToken.getToken());
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> loginHandler(@RequestBody LoginRequest loginRequest, Authentication authentication) {
		
		try {
			
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			
			
			
		} catch(AuthenticationException exception) {
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", "Bad Credentials");
			map.put("status", false);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
			
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
		String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername()).getToken();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		LoginResponse response = new LoginResponse(userDetails.getUsername(), jwtToken, refreshToken);
		
		
		return ResponseEntity.ok(response);
	}
	
}
