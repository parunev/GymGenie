package com.genie.gymgenie.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genie.gymgenie.security.jwt.JwtFilter;
import com.genie.gymgenie.security.jwt.JwtLogout;
import com.genie.gymgenie.security.payload.ApiError;
import com.genie.gymgenie.security.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    private final JwtLogout jwtLogout;
    private final ObjectMapper objectMapper;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(((request, response, authException) ->{
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType("application/json");
                            response.getWriter().write(objectMapper.writeValueAsString(
                                    ApiError.builder()
                                            .path(request.getRequestURI())
                                            .error(authException.getMessage())
                                            .timestamp(LocalDateTime.now())
                                            .build()
                            ));
                        })))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/genie/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/genie/v1/user/**").permitAll()
                        .requestMatchers("/v2/api-docs", "/v3/api-docs",
                                "/v3/api-docs/**", "/swagger-resources",
                                "/swagger-resources/**", "/configuration/ui",
                                "/configuration/security", "/swagger-ui/**",
                                "/webjars/**", "swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/edge-api/v1/auth/logout")
                        .addLogoutHandler(jwtLogout)
                        .logoutSuccessHandler(((request, response, authentication) -> {
                            response.setContentType("application/json");
                            final String authHeader = request.getHeader("Authorization");

                            String message;
                            HttpStatus status;
                            if (authHeader == null || !authHeader.startsWith("Bearer ")){
                                message = "There is no authenticated user.";
                                status = HttpStatus.UNAUTHORIZED;
                            } else {
                                message = "User successfully logged out.";
                                status = HttpStatus.OK;
                            }

                            response.getWriter().write(objectMapper.writeValueAsString(
                                    ApiResponse.builder()
                                            .path(request.getRequestURI())
                                            .message(message)
                                            .timestamp(LocalDateTime.now())
                                            .status(status)
                                            .build()
                            ));
                            SecurityContextHolder.clearContext();
                        })))
                .build();
    }
}
