package com.workout.planner.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequestDTO {
    private String name;
    private String description;
    private String muscleGroup;
    private String equipment;
    private String videoUrl;
    private int weights; // in kg
    private int sets;
    private int reps;
    private Long restTime;
}
