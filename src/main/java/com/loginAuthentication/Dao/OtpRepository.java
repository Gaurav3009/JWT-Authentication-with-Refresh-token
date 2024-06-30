package com.loginAuthentication.Dao;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loginAuthentication.Entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {
	
	@Query("select o from Otp o where o.otp = :otp")
	public Otp findOtpByOtp(@Param("otp") int otp);
	
	@Query("select o from Otp o where o.expirationTime < :expirationTime")
	public List<Otp> findAllByExpirationTimeBefore(@Param("expirationTime") Instant expirationTime);
	
}
