package com.example.rowersi.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsManagerImplementation {
	
	private UserDetailsManager userDetailsManager;
	
	public UserDetailsManagerImplementation(UserDetailsManager userDetailsManager) {
		this.userDetailsManager = userDetailsManager;
	}
	
	public void createUser(String username, String password) {
		UserDetails user = User.builder()
				.username(username)
				.password("{bcrypt}"+encodePassword(password))
				.roles("USER")
				.build();
		
		userDetailsManager.createUser(user);
	}
	
	private String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		return encoder.encode(password);
	}
}
