package com.talenthire.apigateway.filter;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.talenthire.apigateway.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            WebFilterChain chain) {

        System.out.println("Request coming");

        ServerHttpRequest request =
                exchange.getRequest();

        String path =
                request.getURI().getPath();
        
        if (request.getMethod() == org.springframework.http.HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }



        if (path.startsWith("/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")) {

            return chain.filter(exchange);
        }


        

        String header =
                request.getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);


        if (header == null ||
                !header.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(
                            HttpStatus.UNAUTHORIZED);

            return exchange.getResponse()
                    .setComplete();
        }


        String jwt =
                header.substring(7);


        try {



            Claims claims =
                    jwtUtil.validateToken(jwt);

            System.out.println("JWT validated");


            Integer userId =
                    claims.get(
                            "user_id",
                            Integer.class);

            String role =
                    claims.get(
                            "user_role",
                            String.class);


            System.out.println(
                    "User ID: " + userId);

            System.out.println(
                    "Role: " + role);


         

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(
                                            "ROLE_" + role)
                            )
                    );


      

            ServerHttpRequest modifiedRequest =
                    request.mutate()

                            .header(
                                    "X-User-Id",
                                    String.valueOf(userId))

                            .header(
                                    "X-User-Role",
                                    role)

                            .build();



            return chain
                    .filter(
                            exchange.mutate()
                                    .request(
                                            modifiedRequest)
                                    .build()
                    )
                    .contextWrite(
                            ReactiveSecurityContextHolder
                                    .withAuthentication(
                                            authentication)
                    );


        } catch (Exception e) {

            e.printStackTrace();

            exchange.getResponse()
                    .setStatusCode(
                            HttpStatus.UNAUTHORIZED);

            return exchange.getResponse()
                    .setComplete();
        }
    }
}