package com.example.StudentAppWithJWT.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.StudentAppWithJWT.dto.RegisterDto;
import com.example.StudentAppWithJWT.model.Users;
import com.example.StudentAppWithJWT.repository.RoleRepository;
import com.example.StudentAppWithJWT.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepo;

	public void register(RegisterDto registerDto) {
		if (userRepo.existsByUsername(registerDto.getUsername())) {
			throw new RuntimeException("Username already exists");
		}
		Users usr = new Users();
		usr.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		usr.setUsername(registerDto.getUsername());
		usr.setRoles(registerDto.getRoles().stream().map(n -> roleRepo.findByName(n).orElseThrow(() -> new RuntimeException("Role Not found"))).collect(Collectors.toSet()));
		userRepo.save(usr);
	}

	

}
