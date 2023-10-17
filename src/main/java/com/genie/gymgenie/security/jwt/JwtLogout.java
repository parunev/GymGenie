package com.genie.gymgenie.security.jwt;

import com.genie.gymgenie.models.JwtToken;
import com.genie.gymgenie.repositories.JwtTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogout implements LogoutHandler {

    private final JwtTokenRepository jwtTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(JwtFilter.HEADERS[0]);
        final String jwt;

        if (authHeader == null || !authHeader.startsWith(JwtFilter.HEADERS[1])){
            return;
        }

        jwt = authHeader.substring(7);
        JwtToken storedToken = jwtTokenRepository.findByTokenValue(jwt)
                .orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            jwtTokenRepository.save(storedToken);
        }
    }
}
