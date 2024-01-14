package com.example.rowersi.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.rowersi.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDaoImplementation implements UserDao{
	
	private EntityManager entityManager;
	
	@Autowired
	public UserDaoImplementation(EntityManager entityManagery) {
		this.entityManager = entityManagery;
	}

	@Override
	public List<User> getUsers() {
		TypedQuery<User> query = this.entityManager.createQuery("FROM User", User.class);

		return query.getResultList() ;
	}

}
