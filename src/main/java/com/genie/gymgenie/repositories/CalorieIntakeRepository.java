package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.diet.CalorieIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CalorieIntakeRepository extends JpaRepository<CalorieIntake, Long> {
}
