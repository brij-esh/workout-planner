package com.workout.planner.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workout.planner.dtos.WorkoutRequestDTO;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.Workout;
import com.workout.planner.services.WorkoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workouts")
@Validated
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return new ResponseEntity<>(workoutService.getWorkouts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkout(@PathVariable String id) {
        return new ResponseEntity<>(workoutService.getWorkout(id), HttpStatus.OK);
    }

    @PostMapping("/{userEmail}")
    public ResponseEntity<Workout> createWorkout(@PathVariable String userEmail, @RequestBody WorkoutRequestDTO workout) {
        Workout createdWorkout = workoutService.createWorkout(userEmail, workout);
        return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
    }

    @PutMapping("/{userEmail}/{workoutId}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable String userEmail, @PathVariable String workoutId, @RequestBody WorkoutRequestDTO workout) {
        Workout updatedWorkout = workoutService.updateWorkout(userEmail, workoutId, workout);
        return new ResponseEntity<>(updatedWorkout, HttpStatus.OK);
    }

    @DeleteMapping("/{userEmail}/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable String userEmail, @PathVariable String workoutId) {
        workoutService.deleteWorkout(userEmail, workoutId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/exercises")
    public ResponseEntity<List<Exercise>> getExercises(@PathVariable String id) {
        List<Exercise> exercises = workoutService.getExercises(id);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @PostMapping("/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<Exercise> addExercise(@PathVariable String workoutId, @PathVariable String exerciseId) {
        Exercise addedExercise = workoutService.addExercise(workoutId, exerciseId);
        return new ResponseEntity<>(addedExercise, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/exercises/{exerciseId}")
    public ResponseEntity<Exercise> getExercise(@PathVariable String id, @PathVariable String exerciseId) {
        Exercise exercise = workoutService.getExercise(id, exerciseId);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PutMapping("/{id}/exercises/{exerciseId}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable String id, @PathVariable String exerciseId, @RequestBody Exercise exercise) {
        Exercise updatedExercise = workoutService.updateExercise(id, exerciseId, exercise);
        return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/exercises/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable String id, @PathVariable String exerciseId) {
        workoutService.deleteExercise(id, exerciseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
