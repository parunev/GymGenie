package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.DietaryRegime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietaryRegimeRepository extends JpaRepository<DietaryRegime, Long> {
}
