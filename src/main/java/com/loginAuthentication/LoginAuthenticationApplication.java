package com.loginAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoginAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginAuthenticationApplication.class, args);
	}

}

