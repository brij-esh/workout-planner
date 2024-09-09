package com.workout.planner.models;
import java.time.LocalDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.workout.planner.enums.TimeOfDay;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    private String id;
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User user;
    private String name;
    private String description;
    private Long duration;
    private TimeOfDay timeOfDay;
    @OneToMany(targetEntity = Exercise.class, fetch = FetchType.EAGER)
    private List<Exercise> exercises;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}