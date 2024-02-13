package com.example.rowersi.security.filter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.rowersi.util.jwt.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

  private JwtTokenUtil jwtTokenUtil;
  private UserDetailsManager userDetailsManager;

  public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsManager userDetailsManager) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsManager = userDetailsManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // TODO Auto-generated method stub
    // System.out.println("localAddr" + request.getLocalAddr());
    // System.out.println("LocalPort" + request.getLocalPort());
    System.out.println(request.getHeader("Authorization"));

    String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
    // System.out.println(bearer.isEmpty());

    if (bearer == null || bearer.isEmpty() || !bearer.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String token = bearer.split(" ")[1].trim();

    try {
      if (!jwtTokenUtil.isValidAccessToken(token)) {
        filterChain.doFilter(request, response);
        return;
      }
    } catch (SignatureException | IOException | ServletException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    try {
      UserDetails user =
          userDetailsManager.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));

      SecurityContext context = SecurityContextHolder.createEmptyContext();

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          user, null, user == null ? List.of() : user.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      context.setAuthentication(authentication);

      SecurityContextHolder.setContext(context);
      filterChain.doFilter(request, response);
    } catch (UsernameNotFoundException | SignatureException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // if (bearer != null) {
    // System.out.println(bearer);
    // } else {
    // throw new AccessDeniedException("Access denied");
    // }

  }
}
