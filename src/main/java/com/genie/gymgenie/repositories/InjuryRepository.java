package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.Injury;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InjuryRepository extends JpaRepository<Injury, Long> {
}
