package com.talenthire.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.talenthire.apigateway.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtFilter implements GlobalFilter,Ordered{
	
private final JwtUtil jwtUtil;
	

	@Override
	public int getOrder() {
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		System.out.println("Request coming");
		  ServerHttpRequest request = exchange.getRequest();

	        String path = request.getURI().getPath();

	        // Skip JWT validation for Auth APIs
	        if (path.startsWith("/auth")) {
	            return chain.filter(exchange);
	        }

	        String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

	        if (header == null || !header.startsWith("Bearer ")) {

	            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

	            return exchange.getResponse().setComplete();
	        }

	        String jwt = header.substring(7);

	        try {

	            Claims claims = jwtUtil.validateToken(jwt);

	            Integer userId = claims.get("user_id", Integer.class);
	            String role = claims.get("user_role", String.class);
	            
	            ServerHttpRequest modifiedRequest=request.mutate()
	            		.header("X-User-Id",String.valueOf(userId))
	            		.header("X-User-Role", role)
	            		.build();
	            		
	            				

	            // JWT is valid.
	            // Continue request to the destination service.

	            return chain.filter(exchange.mutate().request(modifiedRequest).build());

	        } catch (Exception e) {

	            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

	            return exchange.getResponse().setComplete();
	        }
	}

}
