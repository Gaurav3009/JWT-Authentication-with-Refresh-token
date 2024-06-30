package com.loginAuthentication.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.loginAuthentication.Dao.RefreshTokenRepository;
import com.loginAuthentication.Dao.UserRepository;
import com.loginAuthentication.Entity.Otp;
import com.loginAuthentication.Entity.RefreshToken;

@Service
public class RefreshTokenService {
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${spring.app.refreshTokenExpirationMs}")
	private int refreshTokenExpirationMs;
	
	public RefreshToken createRefreshToken(String username) {
		
		RefreshToken refreshToken = new RefreshToken(
				UUID.randomUUID().toString(),
				Instant.now().plusMillis(refreshTokenExpirationMs),
				this.userRepository.getUserByUserName(username)
				);
		
		this.refreshTokenRepository.save(refreshToken);
		
		return refreshToken;	
	}
	
	public Optional<RefreshToken> findByToken(String token) {
		return this.refreshTokenRepository.findByToken(token);
	}
	
	public RefreshToken findTokenByUserName(String username) {
		return this.refreshTokenRepository.getRefreshTokenByUserId(username);
	}
	
	public RefreshToken verifyExpiration(RefreshToken token) {
		if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
			this.refreshTokenRepository.delete(token);
			throw new RuntimeException(token.getToken() + "Refresh Token is expired");
		}
		
		return token;
	}
	
	@Scheduled(fixedDelay = 360000)
	public void removeExpiredRefreshToken() {
		Instant current = Instant.now();
		List<RefreshToken> expiredTokens = this.refreshTokenRepository.findAllByExpirationTimeBefore(current);
		this.refreshTokenRepository.deleteAll(expiredTokens);
	}
	
	
}
