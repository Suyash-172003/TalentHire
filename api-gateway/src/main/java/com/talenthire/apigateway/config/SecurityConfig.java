package com.talenthire.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.talenthire.apigateway.filter.JwtFilter;
import com.talenthire.apigateway.util.JwtUtil;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            JwtUtil jwtUtil) {

        JwtFilter jwtFilter = new JwtFilter(jwtUtil);

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .addFilterAt(
                        jwtFilter,
                        SecurityWebFiltersOrder.AUTHENTICATION
                )

                .authorizeExchange(exchange -> exchange

                        // IMPORTANT: Allow browser CORS preflight
                        .pathMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        // Auth
                        .pathMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        )
                        .permitAll()

                        // Recruiter
                        .pathMatchers(
                                "/job/create",
                                "/job/update/**",
                                "/job/close/**",
                                "/job/my/**",
                                "/application/interview/schedule",
                                "/assessment/create",
                                "/assessment/*/coding/upload",
                                "/assessment/*/coding-question",
                                "/assessment/coding-question/*/testcase",
                                "/assessment/assign",
                                "/assessment/*/results",
                                "/resume/recruiter/**"
                        )
                        .hasRole("RECRUITER")

                        // Candidate
                        .pathMatchers(
                                "/application/apply/**",
                                "/application/my/**",
                                "/application/interview/candidate/**",
                                "/resume/**"
                        )
                        .hasRole("CANDIDATE")

                        // Common job endpoints
                        .pathMatchers(
                                "/job/**",
                                "/application/interview/application/**"
                        )
                        .hasAnyRole(
                                "CANDIDATE",
                                "RECRUITER"
                        )

                        .anyExchange()
                        .authenticated()
                )

                .build();
    }
}