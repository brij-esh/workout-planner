package com.workout.planner.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String description;
    private String muscleGroup;
    private String equipment;
    private String videoUrl;
    private int sets;
    private int reps;
    private Long restTime; // stored in minutes

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    private User creator; // Exercise creator

    @ManyToMany(mappedBy = "exercises", fetch = FetchType.LAZY)
    private List<Workout> workouts;

    public Duration getRestTime() {
        return Duration.ofMinutes(restTime);
    }
}
