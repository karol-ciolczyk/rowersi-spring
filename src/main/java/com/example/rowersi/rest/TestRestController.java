package com.example.rowersi.rest;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {
	
	@GetMapping("/")
	public String hello() {
		return "Hello World !!";
	}
	
	@GetMapping("/mysql")
	public String mysql() throws URISyntaxException {
		URI jdbUri = new URI(System.getenv("JAWSDB_URL"));

	    String username = jdbUri.getUserInfo().split(":")[0];
	    String password = jdbUri.getUserInfo().split(":")[1];
	    String port = String.valueOf(jdbUri.getPort());
	    String jdbUrl = "jdbc:mysql://" + jdbUri.getHost() + ":" + port + jdbUri.getPath();
		return "username: " + username + ", password: " + password + " jdbUrl: " + jdbUrl;
	}
}
