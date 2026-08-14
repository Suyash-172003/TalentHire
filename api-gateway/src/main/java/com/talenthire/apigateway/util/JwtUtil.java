package com.talenthire.apigateway.util;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	
	@Value("${jwt.expiration.time}")//Spring Expression Language - SpEL
	private long expTime;
	
	@Value("${jwt.secret.key}")
	private String key;
	
	private SecretKey secretKey;
	
	@PostConstruct
	public void init() {
		secretKey=Keys.hmacShaKeyFor(key.getBytes());
	}
	
	public Claims validateToken(String jwt)
	{
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(jwt) //throws Exception in case of invalid (expired | tampered | invalid)
				.getPayload();
	}

}
