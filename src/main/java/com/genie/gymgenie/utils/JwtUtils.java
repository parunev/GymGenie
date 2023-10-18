package com.genie.gymgenie.utils;

import com.genie.gymgenie.models.JwtToken;
import com.genie.gymgenie.models.User;
import com.genie.gymgenie.repositories.JwtTokenRepository;
import com.genie.gymgenie.security.GenieLogger;
import com.genie.gymgenie.security.jwt.JwtService;
import com.nimbusds.jose.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtTokenRepository repository;
    private final JwtService jwtService;
    private static final GenieLogger genie = new GenieLogger(JwtUtils.class);

    public String generateToken(User user){
        return jwtService.generateToken(user);
    }

    public String extractEmail(String refreshToken){
        return jwtService.extractEmail(refreshToken);
    }

    public boolean isValid(String refreshToken, User user){
        return jwtService.isTokenValid(refreshToken, user);
    }

    public void saveToken(JwtToken token){
        repository.save(token);
    }

    public void revokeAndSaveTokens(User user) {
        genie.info("Revoking and saving tokens for user: {}", user.getUsername());

        List<JwtToken> validTokens = repository.findAllValidTokenByUserId(user.getId());

        if (validTokens.isEmpty()){
            genie.info("No valid tokens found to revoke");
            return;
        }

        validTokens.forEach(jwtToken -> {
            jwtToken.setExpired(true);
            jwtToken.setRevoked(true);
        });

        repository.saveAll(validTokens);
    }

    public Pair<String, String> generateJwtTokens(User user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        JwtToken token = JwtToken.builder()
                .user(user)
                .tokenValue(accessToken)
                .expired(false)
                .revoked(false)
                .build();

        repository.save(token);
        genie.info("Tokens saved for user: {}", user.getUsername());

        return Pair.of(accessToken, refreshToken);
    }
}
