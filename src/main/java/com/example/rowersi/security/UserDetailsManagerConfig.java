//package com.example.rowersi.security;
//
//import javax.sql.DataSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//
//@Configuration
//public class UserDetailsManagerConfig {
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        UserDetails user = User.builder()
//            .username("user")
//            .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//            .roles("USER")
//            .build();
//
//        UserDetails admin = User.builder()
//            .username("admin")
//            .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
//            .roles("USER", "ADMIN")
//            .build();

    	
//    	UserDetails karcio = User.builder()
//    			.username("karcio")
//    			.password("{bcrypt}$2a$12$.xWLZeIulFP06leVZGFZbOtZmf90EuKEsqWdLchrkm/J29NoVeK2W")
//    			.roles("USER", "ADMIN")
//    			.build();

//    	JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//    	users.createUser(karcio);
//    	users.createUser(admin);
//    	return users;
//    }
//}
