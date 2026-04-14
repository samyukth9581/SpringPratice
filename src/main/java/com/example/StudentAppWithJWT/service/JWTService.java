package com.example.StudentAppWithJWT.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	
	private String secret = "my-super-secret-key-for-production";
	private long expiration = 3600000;

	public String generateToken(String username) {
		Map<String, Object> clms = new HashMap<>();
		return Jwts.builder().claims().add(clms).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration)).and().signWith(getKey()).compact();
	}

	private Key getKey() {
		SecretKey sk = Keys.hmacShaKeyFor(secret.getBytes());
		return sk;
	}

	public String extractUserName(String token) {
		return extractAllClaims(token).getSubject();
	}

	@SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean isvalidToken(String token, UserDetails usrdtls) {
		String userName = extractUserName(token);
		return userName.equalsIgnoreCase(usrdtls.getUsername()) && !isExpired(token);
	}

	private boolean isExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}

}
