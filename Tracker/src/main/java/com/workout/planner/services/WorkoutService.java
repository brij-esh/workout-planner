package com.workout.planner.services;

import java.util.List;

import com.workout.planner.dtos.WorkoutRequestDTO;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.Workout;

public interface WorkoutService {
    public Workout createWorkout(String username, WorkoutRequestDTO workoutRequestDTO);
    public List<Workout> getWorkouts();
    public Workout getWorkout(String workoutId);
    public Workout updateWorkout(String username, String workoutId, WorkoutRequestDTO workoutRequestDTO);
    public void deleteWorkout(String username, String workoutId);
    public List<Exercise> getExercises(String workoutId);
    public Exercise addExercise(String username, String workoutId, String exerciseId);
    public Exercise getExercise(String username, String workoutId, String exerciseId);
    public Exercise updateExercise(String username, String workoutId, String exerciseId, Exercise exercise);
    public void deleteExercise(String workoutId, String exerciseId);
}
