package com.genie.gymgenie.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Authority implements GrantedAuthority {

    AUTHORITY_USER("ROLE_USER"),
    AUTHORITY_ADMIN("ROLE_ADMIN");

    private final String authorityName;

    @Override
    public String getAuthority() {
        return this.authorityName;
    }
}
