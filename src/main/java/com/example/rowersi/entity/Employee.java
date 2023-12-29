package com.example.rowersi.entity;

import jakarta.persistence.*;

@Entity
@Table(name="employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lsatName;
	
	@Column(name="email")
	private String email;
	
	public Employee() {}

	public Employee(String firstName, String lsatName, String email) {
		super();
		this.firstName = firstName;
		this.lsatName = lsatName;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLsatName() {
		return lsatName;
	}

	public void setLsatName(String lsatName) {
		this.lsatName = lsatName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lsatName=" + lsatName + ", email=" + email + "]";
	}
	
	
}
