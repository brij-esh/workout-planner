package com.workout.planner.exception;

public class ExerciseAlreadyExistException extends RuntimeException {
    public ExerciseAlreadyExistException(String message) {
        super(message);
    }

}
