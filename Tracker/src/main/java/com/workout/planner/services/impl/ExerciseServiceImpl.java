package com.workout.planner.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.workout.planner.dtos.ExerciseRequestDTO;
import com.workout.planner.exception.UserNotFoundException;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.User;
import com.workout.planner.repositories.ExerciseRepository;
import com.workout.planner.repositories.UserRepository;
import com.workout.planner.services.ExerciseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{

    private final ExerciseRepository exerciseRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private static final String EXERCISE_NOT_FOUND_ERROR = "Exercise not found with id: ";
    private static final String USER_NOT_FOUND_ERROR = "User not found with email: ";

    @Override
    public Exercise createExercise(String userEmail, ExerciseRequestDTO exerciseRequestDTO) {
        if(!userRepository.existsByEmail(userEmail)) {
            log.error(USER_NOT_FOUND_ERROR, userEmail);
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR + userEmail);
        }
        User user = userRepository.findByEmail(userEmail).get();
        Exercise exercise = modelMapper.map(exerciseRequestDTO, Exercise.class);
        exercise.setCreator(user);
        exercise.setCreatedAt(LocalDateTime.now());
        exercise.setUpdatedAt(LocalDateTime.now());
        exercise = exerciseRepository.save(exercise);
        user.getCreatedExercises().add(exercise);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return exercise;
    }

    @Override
    public Exercise updateExercise(String userEmail, String exerciseId,  ExerciseRequestDTO exerciseRequestDTO) {
        if(!userRepository.existsByEmail(userEmail)) {
            log.error(USER_NOT_FOUND_ERROR, userEmail);
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR + userEmail);
        }
        if(!exerciseRepository.existsById(exerciseId)) {
            log.error(EXERCISE_NOT_FOUND_ERROR, exerciseId);
            throw new UserNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        if(!exercise.getCreator().getEmail().equals(userEmail)) {
            log.error("User with email: {} is not the creator of the exercise with id: {}", userEmail, exerciseId);
            throw new UserNotFoundException("User with email: " + userEmail + " is not the creator of the exercise with id: " + exerciseId);
        }
        if(exerciseRequestDTO.getName() != null) {
            exercise.setName(exerciseRequestDTO.getName());
        }
        if(exerciseRequestDTO.getEquipment() != null) {
            exercise.setEquipment(exerciseRequestDTO.getEquipment());
        }
        if(exerciseRequestDTO.getMuscleGroup() != null) {
            exercise.setMuscleGroup(exerciseRequestDTO.getMuscleGroup());
        }
        if(exerciseRequestDTO.getDescription() != null) {
            exercise.setDescription(exerciseRequestDTO.getDescription());
        }
        if(exerciseRequestDTO.getReps() != 0) {
            exercise.setReps(exerciseRequestDTO.getReps());
        }
        if(exerciseRequestDTO.getSets() != 0) {
            exercise.setSets(exerciseRequestDTO.getSets());
        }
        if(exerciseRequestDTO.getWeights() != 0) {
            exercise.setWeights(exerciseRequestDTO.getWeights());
        }
        if(exerciseRequestDTO.getRestTime() != 0) {
            exercise.setRestTime(exerciseRequestDTO.getRestTime());
        }
        exercise.setUpdatedAt(LocalDateTime.now());
        return exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExercise(String userEmail, String exerciseId) {
        if(!userRepository.existsByEmail(userEmail)) {
            log.error(USER_NOT_FOUND_ERROR, userEmail);
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR + userEmail);
        }
        if(!exerciseRepository.existsById(exerciseId)) {
            log.error(EXERCISE_NOT_FOUND_ERROR, exerciseId);
            throw new UserNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        if(!exercise.getCreator().getEmail().equals(userEmail)) {
            log.error("User with email: {} is not the creator of the exercise with id: {}", userEmail, exerciseId);
            throw new UserNotFoundException("User with email: " + userEmail + " is not the creator of the exercise with id: " + exerciseId);
        }
        exerciseRepository.deleteById(exerciseId);
    }

    @Override
    public Exercise getExercise(String exerciseId) {
        if(!exerciseRepository.existsById(exerciseId)) {
            log.error(EXERCISE_NOT_FOUND_ERROR, exerciseId);
            throw new UserNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        return exerciseRepository.findById(exerciseId).get();
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

}
