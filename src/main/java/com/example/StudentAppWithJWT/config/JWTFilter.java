package com.example.StudentAppWithJWT.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.StudentAppWithJWT.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTService jwtService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		String userName = null;
		String authHeader = request.getHeader("Authorization");
		if(authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}
		if(token != null) {
			userName = jwtService.extractUserName(token);
			System.out.println(userName);
		}
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails usrdtls = userDetailsService.loadUserByUsername(userName);
			if(jwtService.isvalidToken(token, usrdtls)) {
				UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(usrdtls, null, usrdtls.getAuthorities());
				System.out.println(usrdtls.getAuthorities().toString());
				authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
