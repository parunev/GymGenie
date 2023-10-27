package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findByIdAndUser(Long workoutId, User user);
}
