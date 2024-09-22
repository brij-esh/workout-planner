package com.workout.planner.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.workout.planner.enums.TimeOfDay;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;
    private Long duration; // stored in minutes
    private TimeOfDay timeOfDay;

    @ManyToMany(mappedBy = "workouts", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @Builder.Default
    private List<User> participants = new ArrayList<>(); // Users participating in the workout

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User creator; // Workout creator

    @ManyToMany
    @JoinTable(
        name = "workout_exercises",
        joinColumns = @JoinColumn(name = "workout_id"),
        inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    @Builder.Default
    private List<Exercise> exercises = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Duration getDuration() {
        return Duration.ofMinutes(duration);
    }

    @JsonInclude
    public String getCreatorName() {
        return creator.getFirstName()+" "+creator.getLastName();
    }
}
