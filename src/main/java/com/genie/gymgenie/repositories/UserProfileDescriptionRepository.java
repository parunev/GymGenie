package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.UserProfile;
import com.genie.gymgenie.models.UserProfileDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileDescriptionRepository extends JpaRepository<UserProfileDescription, Long> {
    Optional<UserProfileDescription> findByUserProfile(UserProfile userProfile);
}
