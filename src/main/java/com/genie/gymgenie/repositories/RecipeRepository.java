package com.genie.gymgenie.repositories;

import com.genie.gymgenie.models.Recipe;
import com.genie.gymgenie.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM GENIE_RECIPE r " +
    "WHERE r.workout.user = :user" +
    " AND (:workoutId IS NULL OR r.workout.id = :workoutId)" +
    " AND (:healthScoreMin IS NULL OR r.healthScore >= :healthScoreMin)" +
    " AND (:healthScoreMax IS NULL OR r.healthScore <= :healthScoreMax)" +
    " AND (:recipeTitle IS NULL OR r.title LIKE %:recipeTitle%)" +
    " AND (:recipeSummary IS NULL OR r.summary LIKE %:recipeSummary%)")
    Page<Recipe> findAllByWorkoutUser(
            @Param("user")User user,
            @Param("workoutId")Long workoutId,
            @Param("healthScoreMin") Double healthScoreMin,
            @Param("healthScoreMax") Double healthScoreMax,
            @Param("recipeTitle")String recipeTitle,
            @Param("recipeSummary") String recipeSummary,
            Pageable pageable);

    Optional<Recipe> findByIdAndWorkoutUser(Long recipeId, User user);
}
