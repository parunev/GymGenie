package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.FitnessRegime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessRegimeRepository extends JpaRepository<FitnessRegime, Long> {
}
