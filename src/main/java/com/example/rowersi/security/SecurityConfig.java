
package com.example.rowersi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import com.example.rowersi.security.filter.JwtTokenFilter;
import com.example.rowersi.util.jwt.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private UserDetailsManager userDetailsManager;
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  public void setUserDetailsManager(UserDetailsManager userDetailsManager,
      JwtTokenUtil jwtTokenUtil) {
    this.userDetailsManager = userDetailsManager;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager auth)
      throws Exception {
    http
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint((request, response, authException) -> {
              System.out
                  .println("MY EXCEPTION HANDLER !!!!!!!!!!!!!!!!!!!" + authException.getMessage());
              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            }))
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/api/v1/login")
            .permitAll()
            // .requestMatchers("/api/v1/users")
            // .permitAll()
            .anyRequest()
            .authenticated())
        .addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsManager),
            AuthorizationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsManager);

    return new ProviderManager(authenticationProvider);
  }
}

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig{
//
// @Bean
// public UserDetailsManager userDetailsManager(DataSource dataSource) {
// return new JdbcUserDetailsManager(dataSource);
// }
//
// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .csrf((csrf) -> csrf
// .ignoringRequestMatchers(HttpMethod.POST.name(),"/api/v1/users")
//// .ignoringRequestMatchers(HttpMethod.GET.name(),"/api/v1/auth")
// .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
// .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
// )
// .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
// .authorizeHttpRequests((authorize) -> authorize
// .requestMatchers(HttpMethod.POST ,"/api/v1/users").permitAll()
// .requestMatchers(HttpMethod.GET ,"/api/v1/auth").permitAll()
//// .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
// .requestMatchers("/api/v1/admin/**").access(new
// WebExpressionAuthorizationManager("hasRole('ADMIN') && hasRole('USER')"))
// .requestMatchers("/test").permitAll()
// .anyRequest().authenticated()
// )
// .httpBasic(Customizer.withDefaults())
//// .formLogin(Customizer.withDefaults());
//// .formLogin(login ->
//// login.defaultSuccessUrl("https://rowersi.vercel.app/", true)
//// )
// .formLogin(login-> login.successHandler(new RefererLoginCl()))
// ;
//
// return http.build();
// }
//
// @Bean
// public AuthenticationManager authenticationManager(
// UserDetailsManager userDetailsManager,
// PasswordEncoder passwordEncoder) {
// System.out.println("authentication manager");
// DaoAuthenticationProvider authenticationProvider = new
// DaoAuthenticationProvider();
// authenticationProvider.setUserDetailsService(userDetailsManager);
// authenticationProvider.setPasswordEncoder(passwordEncoder);
//
// return new ProviderManager(authenticationProvider);
// }
//
// @Bean
// public PasswordEncoder passwordEncoder() {
// return PasswordEncoderFactories.createDelegatingPasswordEncoder();
// }
//
// public class RefererLoginCl extends SimpleUrlAuthenticationSuccessHandler {
//
// private final CsrfTokenRepository csrfTokenRepository = new
// HttpSessionCsrfTokenRepository();
//
//
// @Override
// public void onAuthenticationSuccess(HttpServletRequest request,
// HttpServletResponse response,
// Authentication authentication) throws IOException, ServletException {
//
// CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
// String sessionId = request.getSession().getId();
// System.out.println("MY INFORMATION =====+++------" + csrfToken.getToken());
// System.out.println("MY INFORMATION =====+++------" + sessionId);
//
// Cookie csrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
// csrfCookie.setPath("/");
// response.addCookie(csrfCookie);
//
// response.setHeader("Set-Cookie", "JSESSIONID=" + sessionId + "; Path=/");
//
// String redirectUrl = super.determineTargetUrl(request, response);
//
// this.setDefaultTargetUrl("https://rowersi.vercel.app/");
// super.onAuthenticationSuccess(request, response, authentication);
// }
//
// }
//
// }
//
