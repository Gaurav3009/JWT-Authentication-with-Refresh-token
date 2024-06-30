package com.loginAuthentication.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.loginAuthentication.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.username = :username")
	public User getUserByUserName(@Param("username") String username);
	
	@Query("select u from User u where u.email = :email")
	public User findByEmail(@Param("email") String email);
	
}
