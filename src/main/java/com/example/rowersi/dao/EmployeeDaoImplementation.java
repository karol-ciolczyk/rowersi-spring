package com.example.rowersi.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.rowersi.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class EmployeeDaoImplementation implements EmployeeDao {
	
	private EntityManager entityManager;
	
	public EmployeeDaoImplementation(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Employee> getEmployees(){
		
		TypedQuery<Employee> query = entityManager.createQuery("FROM Employee", Employee.class);
		
		return query.getResultList();
	};
}
