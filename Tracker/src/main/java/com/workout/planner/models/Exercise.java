package com.workout.planner.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

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
    private int weights; // in kg
    private int sets;
    private int reps;
    private Long restTime; // stored in minutes

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
     @ToStringExclude
    private User creator; // Exercise creator

    @ManyToMany(mappedBy = "exercises", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Workout> workouts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Duration getRestTime() {
        return Duration.ofMinutes(restTime);
    }

    @JsonInclude
    public String getCreatorName() {
        return creator.getFirstName()+" "+creator.getLastName();
    }
}
