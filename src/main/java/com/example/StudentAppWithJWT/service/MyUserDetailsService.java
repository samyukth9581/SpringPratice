package com.example.StudentAppWithJWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.StudentAppWithJWT.model.UserPrincipal;
import com.example.StudentAppWithJWT.model.Users;
import com.example.StudentAppWithJWT.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users usr = userRepo.findByUsername(username);
		if(usr == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPrincipal(usr);
	}
	
	

}
