package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.diet.main.OurSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OurSuggestionRepository extends JpaRepository<OurSuggestion, Long> {
}
