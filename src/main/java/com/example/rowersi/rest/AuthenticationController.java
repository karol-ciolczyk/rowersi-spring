package com.example.rowersi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.rowersi.util.jwt.JwtTokenUtil;
import com.example.rowersi.util.jwt.TokenCO;
import jakarta.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("${api.prefix}")
public class AuthenticationController {

  private JwtTokenUtil jwtTokenUtil;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      JwtTokenUtil jwtTokenUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @GetMapping("/auth")
  public Object getUserInfo() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication auth = context.getAuthentication();
    Object details = auth.getPrincipal();
    return auth;
  }

  @GetMapping("/login/check")
  public Object checkUser() {
    SecurityContext context = SecurityContextHolder.getContext();
    return context;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
      HttpServletResponse response) {
    try {
      Authentication authentication = this.authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),
              loginRequest.password()));

      User user = (User) authentication.getPrincipal();

      GrantedAuthority role = user.getAuthorities().stream().findFirst().get();

      TokenCO tokennnn = jwtTokenUtil.generateTokens(user, role.getAuthority());

      // Cookie cookie = new Cookie("Access_token", tokennnn.getAccess_token());
      // cookie.setHttpOnly(true);
      // cookie.setValue("sdfsdfdsf");
      // cookie.setSecure(true);
      // cookie.setAttribute("token", "tokenAAAAAA");
      // response.addCookie(cookie);

      ResponseCookie springCookie = ResponseCookie
          .from("token", "token_value_sdfdsfhhh")
          .httpOnly(true)
          .secure(true)
          .sameSite("None")
          // .path("/")
          .maxAge(60)
          .domain("localhost:8080")
          .build();

      HttpHeaders headers = new HttpHeaders();
      headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + tokennnn.getAccess_token());
      headers.add(HttpHeaders.SET_COOKIE, springCookie.toString());


      return new ResponseEntity<>(headers, HttpStatus.OK);
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
