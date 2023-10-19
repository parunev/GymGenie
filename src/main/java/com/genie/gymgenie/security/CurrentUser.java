package com.genie.gymgenie.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.genie.gymgenie.utils.ExceptionThrower.resourceException;

@Component
@NoArgsConstructor(access = AccessLevel.NONE)
public class CurrentUser {

    public static UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }

        throw resourceException("No authentication presented", HttpStatus.UNAUTHORIZED);
    }
}
