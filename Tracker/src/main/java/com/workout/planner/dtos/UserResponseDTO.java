package com.workout.planner.dtos;
import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

import com.workout.planner.enums.Gender;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.Workout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor
public class UserResponseDTO {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private Gender gender;
    private int age;
    private String email;
    private Double weight;
    private Double height;
    private String goal;
    private Blob profilePicture;
    private List<Workout> workouts;
    private List<Exercise> createdExercises;
    private List<Workout> createdWorkouts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
