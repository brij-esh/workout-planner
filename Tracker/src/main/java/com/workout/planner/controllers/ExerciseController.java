package com.workout.planner.controllers;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.workout.planner.dtos.ExerciseRequestDTO;
import com.workout.planner.models.Exercise;
import com.workout.planner.services.ExerciseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping("/{username}")
    public ResponseEntity<Exercise> createExercise(@PathVariable String username, @RequestBody ExerciseRequestDTO exercise) {
        Exercise createdExercise = exerciseService.createExercise(username, exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }


    @PostMapping("/update-exercise/{username}/{exerciseId}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable String username, @PathVariable String exerciseId, @RequestBody ExerciseRequestDTO exercise) {
        Exercise updatedExercise = exerciseService.updateExercise(username, exerciseId, exercise);
        return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
    }

    @PostMapping("/delete-exercise/{username}/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable String username, @PathVariable String exerciseId) {
        exerciseService.deleteExercise(username, exerciseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/get-exercise/{username}/{exerciseId}")
    public ResponseEntity<Exercise> getExercise(@PathVariable String username, @PathVariable String exerciseId) {
        Exercise exercise = exerciseService.getExercise(username, exerciseId);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PostMapping("/get-all-exercises/{username}")
    public ResponseEntity<List<Exercise>> getAllExercises(@PathVariable String username) {
        List<Exercise> exercises = exerciseService.getAllExercises(username);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

}
