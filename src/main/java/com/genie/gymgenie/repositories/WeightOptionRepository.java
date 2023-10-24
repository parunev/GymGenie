package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.diet.main.WeightOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightOptionRepository extends JpaRepository<WeightOption, Long> {
}
