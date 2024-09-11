package com.workout.planner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workout.planner.models.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {

}
