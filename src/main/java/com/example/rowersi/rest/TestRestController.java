package com.example.rowersi.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rowersi.dao.EmployeeDao;
import com.example.rowersi.entity.Employee;

@RestController
public class TestRestController {
	
	private EmployeeDao employeeDao;
	
	public TestRestController(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	@GetMapping("/")
	public String hello() {
		return "Hello World !!";
	}

	@GetMapping("/employees")
	public List<Employee> employees() {
		
		List<Employee> employees = this.employeeDao.getEmployees();
		
		return employees;
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
