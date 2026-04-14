package com.example.StudentAppWithJWT.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
	@GetMapping("allStudents")
	public String allStudents() {
		return "Students fetched successfully";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("createStudent")
	public String createStudent() {
		return "Student Created";
	}
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	@GetMapping("/studentRole")
	public String roleBased() {
		return "Only student can access";
	}
}
