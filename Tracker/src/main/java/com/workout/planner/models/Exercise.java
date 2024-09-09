package com.workout.planner.models;

import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "exercises")
public class Exercise {
    @Id
    private String id;
    private String name;
    private String description;
    private String muscleGroup;
    private String equipment;
    private String videoUrl;
    private int sets;
    private int reps;
    private Duration restTime;
    @ManyToOne(targetEntity = User.class)
    @JsonIgnore
    private User user;
    @ManyToOne(targetEntity = Workout.class)
    @JsonIgnore
    private Workout workout;

}
