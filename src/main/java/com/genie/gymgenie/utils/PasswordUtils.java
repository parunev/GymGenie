package com.genie.gymgenie.utils;

import com.genie.gymgenie.security.GenieLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.genie.gymgenie.utils.ExceptionThrower.authException;

@Component
@RequiredArgsConstructor
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;
    private final GenieLogger genie = new GenieLogger(PasswordUtils.class);

    public boolean isThePasswordValid(String oldPassword, String password, String newPassword, String confirmPassword){
        if (!passwordEncoder.matches(oldPassword, password)){
            genie.warn("The provided old password does not match the user one");
            throw authException("The provided old password does not match your current one", HttpStatus.BAD_REQUEST);
        }

        if (!Objects.equals(newPassword, confirmPassword)){
            genie.warn("Additional validation entered. New passwords does not match");
            throw authException("The provided passwords does not match", HttpStatus.BAD_REQUEST);
        }

        return true;
    }

    public boolean samePassword(String password, String requestPassword){
        return passwordEncoder.matches(password, requestPassword);
    }


    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
