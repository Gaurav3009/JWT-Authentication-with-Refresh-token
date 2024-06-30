package com.loginAuthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loginAuthentication.Dao.UserRepository;
import com.loginAuthentication.Entity.User;
import com.loginAuthentication.configuration.CustomUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void saveUser(User user) {
		this.userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.userRepository.getUserByUserName(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User not fould !!");
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}
	
	public User loadUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
}
