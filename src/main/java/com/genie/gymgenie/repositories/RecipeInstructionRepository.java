package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.RecipeInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeInstructionRepository extends JpaRepository<RecipeInstruction, Long> {
}
