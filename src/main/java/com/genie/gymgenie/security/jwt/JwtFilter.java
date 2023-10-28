package com.genie.gymgenie.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genie.gymgenie.models.User;
import com.genie.gymgenie.repositories.JwtTokenRepository;
import com.genie.gymgenie.repositories.UserRepository;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.exception.ResourceNotFoundException;
import com.genie.gymgenie.security.payload.ApiError;
import com.genie.gymgenie.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.genie.gymgenie.utils.CurrentRequest.getCurrentRequest;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final JwtTokenRepository jwTokenRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final GenieLogger genie = new GenieLogger(JwtFilter.class);
    private static final String CORRELATION_ID = "correlationId";
    static final String[] HEADERS = {"Authorization", "Bearer "};
    private static final String DEFAULT_MESSAGE = "Authentication failed {} {}";


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws IOException {

        try {
            MDC.put(CORRELATION_ID, GenieLogger.generateCorrelationId());
            GenieLogger.setLoggerProperties(MDC.get(CORRELATION_ID), request);

            if(request.getRequestURI().contains("/edge-api/")){
                genie.debug("Received request: {} {}",
                        request.getMethod(), request.getRequestURI());
            }

            final String authHeader = request.getHeader(HEADERS[0]);
            final String jwt;
            final String email;

            if (authHeader == null || !authHeader.startsWith(HEADERS[1])) {
                if(request.getRequestURI().contains("/edge-api/")){
                    genie.debug("No JWT token found in the request. Proceeding without authentication.");
                }

                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7);
            email = jwtService.extractEmail(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = findUserByEmail(email);
                UserDetails userDetails = this.userService.loadUserByUsername(user.getUsername());

                boolean isTokenValid = jwTokenRepository.findByTokenValue(jwt)
                        .map(jwToken -> !jwToken.isExpired() && !jwToken.isRevoked())
                        .orElse(false);

                if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                    genie.info("User {} authenticated successfully.", userDetails.getUsername());

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException | IOException | ServletException | JwtValidationException exception) {
            genie.error(DEFAULT_MESSAGE, exception, exception.getCause());
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(objectMapper.writeValueAsString(ApiError.builder()
                    .path(request.getRequestURI())
                    .error(exception.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build()));

        } catch (ResourceNotFoundException e){
            genie.error(DEFAULT_MESSAGE, e, e.getApiError().getError());
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(objectMapper.writeValueAsString(ApiError.builder()
                    .path(request.getRequestURI())
                    .error(e.getApiError().getError())
                    .timestamp(LocalDateTime.now())
                    .build()));
        } finally {
            MDC.remove(CORRELATION_ID);
            GenieLogger.clearLoggerProperties();
        }
    }

    private User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    genie.warn("User with the provided email not found: {}", email);
                    throw new ResourceNotFoundException(ApiError.builder()
                            .path(getCurrentRequest())
                            .error("User with the provided email not found. Please ensure you have created an account")
                            .timestamp(LocalDateTime.now())
                            .build(), HttpStatus.NOT_FOUND);
                }
        );
    }
}
