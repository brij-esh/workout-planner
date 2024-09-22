package com.workout.planner.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.workout.planner.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String phone;
    private String email;
    private Double weight;
    private Double height;
    private String goal;
    private Blob profilePicture;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_workouts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "workout_id")
    )
    @ToStringExclude
    @Builder.Default
    private List<Workout> workouts = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Workout> createdWorkouts = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Exercise> createdExercises = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
