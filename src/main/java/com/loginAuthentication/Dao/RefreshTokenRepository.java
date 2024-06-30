package com.loginAuthentication.Dao;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loginAuthentication.Entity.Otp;
import com.loginAuthentication.Entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
	
	Optional<RefreshToken> findByToken(String token);
	
	@Query("select r from RefreshToken r where r.user.username = :username")
	RefreshToken getRefreshTokenByUserId(@Param("username") String username);
	
	@Query("select o from RefreshToken o where o.expiryDate < :expiryDate")
	public List<RefreshToken> findAllByExpirationTimeBefore(@Param("expiryDate") Instant expiryDate);
}