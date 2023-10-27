package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.CalorieIntake;
import com.genie.gymgenie.models.WeightOption;
import com.genie.gymgenie.models.enums.diet.WeightOptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeightOptionRepository extends JpaRepository<WeightOption, Long> {
    Optional<WeightOption> findByCalorieIntakeAndName(CalorieIntake calorieIntake, WeightOptionType weightOptionType);
}
