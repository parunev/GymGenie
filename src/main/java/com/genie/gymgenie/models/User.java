package com.genie.gymgenie.models;

import com.genie.gymgenie.models.commons.BaseEntity;
import com.genie.gymgenie.models.enums.Authority;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GENIE_USER")
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
public class User extends BaseEntity implements UserDetails {

    @Column(name = "USERNAME", length = 100, nullable = false)
    private String username;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String lastName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTHORITY", nullable = false)
    private Authority authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(authority.getAuthorityName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
