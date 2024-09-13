package com.workout.planner.services;

import java.util.List;

import com.workout.planner.dtos.ExerciseRequestDTO;
import com.workout.planner.models.Exercise;

public interface ExerciseService {
    public Exercise createExercise(String userEmail, ExerciseRequestDTO exerciseRequestDTO);
    public Exercise updateExercise(String userEmail, String exerciseId, ExerciseRequestDTO exerciseRequestDTO);
    public void deleteExercise(String userEmail, String exerciseId);
    public Exercise getExercise(String exerciseId);
    public List<Exercise> getAllExercises();
    
} 