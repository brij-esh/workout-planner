package com.workout.planner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.workout.planner.dtos.ErrorResponse;
import com.workout.planner.exception.ExerciseAlreadyExistException;
import com.workout.planner.exception.ExerciseNotFoundException;
import com.workout.planner.exception.UserAlreadyExistsException;
import com.workout.planner.exception.UserNotFoundException;
import com.workout.planner.exception.WorkoutAlreadyExistException;
import com.workout.planner.exception.WorkoutNotFoundException;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle WorkoutNotFoundException
    @ExceptionHandler(WorkoutNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleWorkoutNotFoundException(WorkoutNotFoundException ex, WebRequest request) {
        log.error("WorkoutNotFoundException: {}", ex.getMessage());
        ErrorResponse<String> response = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle WorkoutAlreadyExistsException
    @ExceptionHandler(WorkoutAlreadyExistException.class)
    public ResponseEntity<ErrorResponse<String>> handleWorkoutAlreadyExistsException(WorkoutAlreadyExistException ex, WebRequest request) {
        log.error("WorkoutAlreadyExistsException: {}", ex.getMessage());
        ErrorResponse<String> response = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Handle ExerciseNotFoundException
    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleExerciseNotFoundException(ExerciseNotFoundException ex, WebRequest request) {
        log.error("ExerciseNotFoundException: {}", ex.getMessage());
        ErrorResponse<String> response = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle ExerciseAlreadyExistException
    @ExceptionHandler(ExerciseAlreadyExistException.class)
    public ResponseEntity<ErrorResponse<String>> handleExerciseAlreadyExistException(ExerciseAlreadyExistException ex, WebRequest request) {
        log.error("ExerciseAlreadyExistException: {}", ex.getMessage());
        ErrorResponse<String> response = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    
    // Handle UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        log.error("UserNotFoundException: {}", ex.getMessage());
        ErrorResponse<String> response = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse<String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
        log.error("UserAlreadyExistsException: {}", ex.getMessage());
        ErrorResponse<String> response = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation exception: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse<Map<String, String>> response = ErrorResponse.<Map<String, String>>builder()
                .message(errors)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Exception: {}", ex.getMessage());
        ErrorResponse<String> response = ErrorResponse.<String>builder()
                .message(ex.getMessage())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
