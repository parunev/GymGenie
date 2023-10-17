package com.genie.gymgenie.service;

import com.genie.gymgenie.repositories.UserRepository;
import com.genie.gymgenie.security.GenieLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final GenieLogger genie = new GenieLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        genie.info("Attempt to load a user by username: {}", username);

        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    genie.warn("User with username {} not found", username);
                    throw new UsernameNotFoundException(
                            "User with username " + username + " not found!"
                    );
                });
    }
}
