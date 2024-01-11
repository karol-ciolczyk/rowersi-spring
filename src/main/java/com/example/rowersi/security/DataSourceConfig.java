package com.example.rowersi.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://ble5mmo2o5v9oouq.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/ym1f3p3sx8cc99ty");
		dataSource.setUsername("r5rqafsslz38mqii");
		dataSource.setPassword("mxfr5o2drpyd92sx");
		
		return dataSource;
	};
};