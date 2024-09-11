package com.workout.planner.dtos;

import com.workout.planner.enums.TimeOfDay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutRequestDTO {
    private String name;
    private String description;
    private Long duration;
    private TimeOfDay timeOfDay;
}
