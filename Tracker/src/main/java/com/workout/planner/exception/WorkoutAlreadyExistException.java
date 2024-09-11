package com.workout.planner.exception;

public class WorkoutAlreadyExistException extends RuntimeException {
    public WorkoutAlreadyExistException(String message) {
        super(message);
    }

}
