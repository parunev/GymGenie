package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    boolean existsByUser(User user);

    Optional<UserProfile> findByUser(User user);
}
