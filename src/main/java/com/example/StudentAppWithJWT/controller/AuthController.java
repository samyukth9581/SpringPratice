package com.example.StudentAppWithJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.StudentAppWithJWT.dto.LoginDto;
import com.example.StudentAppWithJWT.dto.RegisterDto;
import com.example.StudentAppWithJWT.service.JWTService;
import com.example.StudentAppWithJWT.service.UserService;

@RestController
public class AuthController {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		if(authentication.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtService.generateToken(loginDto.getUsername());
			return ResponseEntity.status(HttpStatus.OK).body(token);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login failed");
	}
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterDto registerDto) {
		userService.register(registerDto);
		return "User Registered";
	}
}
