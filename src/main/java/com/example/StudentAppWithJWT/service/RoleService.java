package com.example.StudentAppWithJWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.StudentAppWithJWT.model.Role;
import com.example.StudentAppWithJWT.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepo;
	
	public void addRoles(Role roles) {
		roleRepo.save(roles);
		
		
	}

	
}
