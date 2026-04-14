package com.example.StudentAppWithJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.StudentAppWithJWT.model.Role;
import com.example.StudentAppWithJWT.service.RoleService;

@RestController
public class PublicController {
	
	@Autowired
	RoleService roleService;
	
	@PostMapping("/addRoles")
	public String addRoles(@RequestBody Role roles) {
		roleService.addRoles(roles);
		return "Role add Successfully";
	}
	
}
