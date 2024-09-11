package com.workout.planner.services;

import java.util.List;

import com.workout.planner.dtos.WorkoutRequestDTO;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.Workout;

public interface WorkoutService {
    public Workout createWorkout(WorkoutRequestDTO workoutRequestDTO);
    public List<Workout> getWorkouts();
    public Workout getWorkout(String id);
    public Workout updateWorkout(String id, WorkoutRequestDTO workoutRequestDTO);
    public void deleteWorkout(String id);
    public List<Exercise> getExercises(String id);
    public Exercise addExercise(String id, Exercise exercise);
    public Exercise getExercise(String id, String exerciseId);
    public Exercise updateExercise(String id, String exerciseId, Exercise exercise);
    public void deleteExercise(String id, String exerciseId);
}
