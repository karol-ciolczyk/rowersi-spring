package com.example.rowersi.rest;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsManager userDetailsManager;

	public LoginController(AuthenticationManager authenticationManager, UserDetailsManager userDetailsManager) {
		this.userDetailsManager = userDetailsManager;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest =
        new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

    	Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
		// Successful authentication, you can generate a token or handle as needed
		return ResponseEntity.ok().build();
	}
	
	public record LoginRequest(String username, String password) {
	}

}