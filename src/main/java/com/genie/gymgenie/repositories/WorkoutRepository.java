package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.User;
import com.genie.gymgenie.models.Workout;
import com.genie.gymgenie.models.enums.workout.Objective;
import com.genie.gymgenie.models.enums.workout.WorkoutAreas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<Workout> findByIdAndUser(Long workoutId, User user);

    @Query("SELECT w FROM GENIE_WORKOUT w " +
            "WHERE w.user = :user" +
            " AND (:workoutName IS NULL OR w.workoutName LIKE %:workoutName%)" +
            " AND (:workoutArea IS NULL OR :workoutArea MEMBER OF w.workoutAreas)" +
            " AND (:objective IS NULL OR w.objective = :objective)")
    Page<Workout> findAllByUser(
            User user,
            String workoutName,
            WorkoutAreas workoutArea,
            Objective objective,
            Pageable pageable);
}
