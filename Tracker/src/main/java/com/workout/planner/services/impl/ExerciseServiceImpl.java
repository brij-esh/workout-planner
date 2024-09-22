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
    private static final String USER_NOT_FOUND_ERROR = "User not found with username: ";

    @Override
    public Exercise createExercise(String username, ExerciseRequestDTO exerciseRequestDTO) {
        if(!userRepository.existsByEmail(username)) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR + username);
        }
        User user = userRepository.findByUsername(username).get();
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
    public Exercise updateExercise(String username, String exerciseId,  ExerciseRequestDTO exerciseRequestDTO) {
        if(!userRepository.existsByUsername(username)) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR + username);
        }
        if(!exerciseRepository.existsById(exerciseId)) {
            log.error(EXERCISE_NOT_FOUND_ERROR, exerciseId);
            throw new UserNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        if(!exercise.getCreator().getUsername().equals(username)) {
            log.error("User with username: {} is not the creator of the exercise with id: {}", username, exerciseId);
            throw new UserNotFoundException("User with username: " + username + " is not the creator of the exercise with id: " + exerciseId);
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
    public void deleteExercise(String username, String exerciseId) {
        if(!userRepository.existsByUsername(username)) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR + username);
        }
        if(!exerciseRepository.existsById(exerciseId)) {
            log.error(EXERCISE_NOT_FOUND_ERROR, exerciseId);
            throw new UserNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        if(!exercise.getCreator().getUsername().equals(username)) {
            log.error("User with username: {} is not the creator of the exercise with id: {}", username, exerciseId);
            throw new UserNotFoundException("User with username: " + username + " is not the creator of the exercise with id: " + exerciseId);
        }
        exerciseRepository.deleteById(exerciseId);
    }

    @Override
    public Exercise getExercise(String username, String exerciseId) {
        if(!exerciseRepository.existsById(exerciseId)) {
            log.error(EXERCISE_NOT_FOUND_ERROR, exerciseId);
            throw new UserNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        return exerciseRepository.findById(exerciseId).get();
    }

    @Override
    public List<Exercise> getAllExercises(String username) {
        if (!userRepository.existsByUsername(username)) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND_ERROR + username);
            
        }
        User user = userRepository.findByUsername(username).get();
        return exerciseRepository.findByCreatorId(user.getId());
    }

}
