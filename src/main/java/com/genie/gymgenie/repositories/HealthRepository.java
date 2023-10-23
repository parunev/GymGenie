package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<Health, Long> {
    Optional<Health> findByUserUsername(String username);
}
