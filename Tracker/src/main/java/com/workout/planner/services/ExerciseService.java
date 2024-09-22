package com.workout.planner.services;

import java.util.List;

import com.workout.planner.dtos.ExerciseRequestDTO;
import com.workout.planner.models.Exercise;

public interface ExerciseService {
    public Exercise createExercise(String username, ExerciseRequestDTO exerciseRequestDTO);
    public Exercise updateExercise(String username, String exerciseId, ExerciseRequestDTO exerciseRequestDTO);
    public void deleteExercise(String username, String exerciseId);
    public Exercise getExercise(String username, String exerciseId);
    public List<Exercise> getAllExercises(String username);
    
} 