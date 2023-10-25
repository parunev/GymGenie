package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

}
