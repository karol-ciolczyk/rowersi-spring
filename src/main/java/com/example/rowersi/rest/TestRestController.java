package com.example.rowersi.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {
	
	private DataSource dataSource;
	
	public TestRestController(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@GetMapping("/")
	public String hello() {
		return "Hello World !!";
	}
	@GetMapping("/datasource")
	public String datasource() throws SQLException {
		return "Hello World !!" + this.dataSource.getConnection().getMetaData();
	}
	
//	@GetMapping("/mysql")
//	public String mysql() throws URISyntaxException {
//		URI jdbUri = new URI(System.getenv("JAWSDB_URL"));
//
//	    String username = jdbUri.getUserInfo().split(":")[0];
//	    String password = jdbUri.getUserInfo().split(":")[1];
//	    String port = String.valueOf(jdbUri.getPort());
//	    String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
//		return "username: " + username + ", password: " + password + " jdbUrl: " + jdbUrl;
//	}
}
