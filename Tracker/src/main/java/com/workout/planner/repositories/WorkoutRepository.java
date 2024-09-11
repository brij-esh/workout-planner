package com.workout.planner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workout.planner.models.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, String> {

    Workout findByName(String name);

}
