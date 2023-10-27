package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.Workout;
import com.genie.gymgenie.models.CalorieIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CalorieIntakeRepository extends JpaRepository<CalorieIntake, Long> {
    Optional<CalorieIntake> findByWorkout(Workout workout);
}
