package com.talenthire.auth.utils;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.talenthire.auth.security.CustomUserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtils {
	@Value("${jwt.expiration.time}")//Spring Expression Language - SpEL
	private long expTime;
	
	@Value("${jwt.secret.key}")
	private String key;
	//represents symmetic secret key
	private SecretKey secretKey;
	/*
	 * Creates symmetric secret key , using HMAC-SHA 256 | 512
	 * - at the SC start up
	 */
	@PostConstruct
	public void init() {
		secretKey=Keys.hmacShaKeyFor(key.getBytes());
	}
	//add a method to generate signed JWT (will be called aftter successful login)
	public String generateJwt(CustomUserDetailsImpl userDetails)
	{
		
		Date iat=new Date();
		Date expiresAt=new Date(iat.getTime()+expTime);
		 return Jwts.builder()
				.subject(userDetails.getUsername()) //adds subject of the payload
				.issuedAt(iat) // claim - issuaed at
				.expiration(expiresAt) //claim - exps at
				.claims(Map.of("user_id", userDetails.getUser().getUserId(), 
						"user_role", userDetails.getUser().getUserRole().name()))
				.signWith(secretKey)
				.compact();
	}
	//add a method to validate token & extract claims from the payload
	Claims validateToken(String jwt)
	{
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(jwt) //throws Exception in case of invalid (expired | tampered | invalid)
				.getPayload();
	}

}
