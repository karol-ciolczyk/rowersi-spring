package com.example.rowersi.security;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
    	return new JdbcUserDetailsManager(dataSource);
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf
					.ignoringRequestMatchers(HttpMethod.POST.name(),"/api/v1/users")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
			)
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests((authorize) -> authorize
					.requestMatchers(HttpMethod.POST ,"/api/v1/users").permitAll()
//					.requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
					.requestMatchers("/api/v1/admin/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') && hasRole('USER')"))
					.requestMatchers("/test").permitAll()
					.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(login -> 
				login.defaultSuccessUrl("/")
			);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsManager userDetailsManager,
			PasswordEncoder passwordEncoder) {
		System.out.println("authentication manager");
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsManager);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}