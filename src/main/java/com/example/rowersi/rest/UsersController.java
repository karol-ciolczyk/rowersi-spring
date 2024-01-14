package com.example.rowersi.rest;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rowersi.dao.UserDao;
import com.example.rowersi.entity.User;
import com.example.rowersi.security.UserDetailsManagerImplementation;

@RestController
@RequestMapping("api/v1")
public class UsersController {

	private UserDao userDao;
	private UserDetailsManagerImplementation userDetailsManagerImplementation;
	
	public UsersController(UserDao userDao, UserDetailsManagerImplementation userDetailsManagerImplementation) {
		this.userDao = userDao;
		this.userDetailsManagerImplementation = userDetailsManagerImplementation;
	}
	
	@GetMapping("/admin/users")
	public List<User> getUsers(){
		
		return userDao.getUsers();
	};
	
	@PostMapping("/users")
	public void testMethod(@RequestBody UserCredentials userCredentials) {
//		this.userDetailsManagerImplementation.addUser(userCredentials.username, userCredentials.password);
		String username = userCredentials.username();
		this.userDetailsManagerImplementation.createUser(userCredentials.username, userCredentials.password);
	}
	
	public record UserCredentials(String username, String password) {
	}

}
