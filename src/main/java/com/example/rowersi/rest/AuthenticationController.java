package com.example.rowersi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("${api.prefix}")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  public AuthenticationController(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @GetMapping("/auth")
  public Object getUserInfo() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();
    Object details = auth.getPrincipal();
    return auth;
  }

  @PostMapping("/login")
  public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {

    Authentication authenticationRequest = UsernamePasswordAuthenticationToken
        .unauthenticated(loginRequest.username(), loginRequest.password());

    try {
      Authentication authenticationResponse =
          this.authenticationManager.authenticate(authenticationRequest);

      return ResponseEntity.ok().build();
    } catch (AuthenticationException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getCause());

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  public record LoginRequest(String username, String password) {
  }
}

// setting authenticatedcontexHolder
// SecurityContext context = SecurityContextHolder.createEmptyContext();
// Authentication authentication =
// new TestingAuthenticationToken("username", "password", "ROLE_USER");
// context.setAuthentication(authentication);
//
// SecurityContextHolder.setContext(context);
