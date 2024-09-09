package com.workout.planner.models;

import jakarta.persistence.Id;

import com.workout.planner.enums.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private int age;
    private String phone;
    private String email;
    private Double weight;
    private Double height;
    private String goal;
    private Blob profilePicture;
    @OneToMany(targetEntity = Workout.class, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Workout> workouts;
    @OneToMany(targetEntity = Exercise.class, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Exercise> exercises;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
