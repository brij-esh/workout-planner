package com.workout.planner.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.workout.planner.dtos.WorkoutRequestDTO;
import com.workout.planner.exception.ExerciseNotFoundException;
import com.workout.planner.exception.RequestProcessingException;
import com.workout.planner.exception.UserNotFoundException;
import com.workout.planner.exception.WorkoutAlreadyExistException;
import com.workout.planner.exception.WorkoutNotFoundException;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.User;
import com.workout.planner.models.Workout;
import com.workout.planner.repositories.ExerciseRepository;
import com.workout.planner.repositories.UserRepository;
import com.workout.planner.repositories.WorkoutRepository;
import com.workout.planner.services.WorkoutService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final ModelMapper modelMapper;

    private final WorkoutRepository workoutRepository;

    private final UserRepository userRepository;

    private final ExerciseRepository exerciseRepository;

    private static final String WORKOUT_NOT_FOUND_ERROR = "Workout not found with id: ";
    private static final String EXERCISE_NOT_FOUND_ERROR = "Exercise not found with id: ";
    private static final String USER_NOT_FOUND_ERROR = "User not found with email: {}";
    private static final String USER_NOT_FOUND = "User not found with email: ";

    @Transactional
    @Override
    public Workout createWorkout(String username, WorkoutRequestDTO workoutRequestDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error(USER_NOT_FOUND_ERROR, username);
                    return new UserNotFoundException(USER_NOT_FOUND + username);
                });

        if (workoutRepository.findByName(workoutRequestDTO.getName()) != null) {
            log.error("Workout with name {} already exists", workoutRequestDTO.getName());
            throw new WorkoutAlreadyExistException(
                    "Workout with name " + workoutRequestDTO.getName() + " already exists");
        }

        log.info("Creating workout with name: {}", workoutRequestDTO.getName());
        Workout workout = modelMapper.map(workoutRequestDTO, Workout.class);
        workout.setCreator(user);
        workout.setCreatedAt(LocalDateTime.now());
        workout.setUpdatedAt(LocalDateTime.now());
        workout = workoutRepository.save(workout);

        if (user.getCreatedWorkouts() == null) {
            user.setCreatedWorkouts(new ArrayList<>());
        }
        user.getCreatedWorkouts().add(workout);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return workout;
    }

    @Override
    public List<Workout> getWorkouts() {
        log.info("Fetching all workouts");
        List<Workout> workouts = workoutRepository.findAll();
        if (workouts.isEmpty()) {
            log.error("No workouts found");
            throw new WorkoutNotFoundException("No workouts found");
        }
        return workouts;
    }

    @Override
    public Workout getWorkout(String id) {
        log.info("Fetching workout with id: {}", id);
        return workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id));
    }

    @Override
    public Workout updateWorkout(String username, String id, WorkoutRequestDTO workoutRequestDTO) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND + username);
        }
        log.info("Updating workout with id: {}", id);
        Workout existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id));
        if (!existingWorkout.getCreator().getUsername().equals(username)) {
            log.error("User with email {} is not authorized to update workout with id: {}", username, id);
            throw new RequestProcessingException(
                    "User with username " + username + " is not authorized to update workout with id: " + id);
        }

        if (workoutRequestDTO.getName() != null) {
            existingWorkout.setName(workoutRequestDTO.getName());
        }
        if (workoutRequestDTO.getDescription() != null) {
            existingWorkout.setDescription(workoutRequestDTO.getDescription());
        }
        if (workoutRequestDTO.getDuration()!=null && workoutRequestDTO.getDuration() != 0) {
            existingWorkout.setDuration(workoutRequestDTO.getDuration());
        }
        if (workoutRequestDTO.getTimeOfDay() != null) {
            existingWorkout.setTimeOfDay(workoutRequestDTO.getTimeOfDay());
        }
        existingWorkout.setUpdatedAt(LocalDateTime.now());
        return workoutRepository.save(existingWorkout);
    }

    @Override
    public void deleteWorkout(String username, String workoutId) {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND + username);
        }
        log.info("Deleting workout with id: {}", workoutId);
        Workout existingWorkout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + workoutId));
        if (!existingWorkout.getCreator().getUsername().equals(username)) {
            log.error("User with username {} is not authorized to delete workout with id: {}",username, workoutId);
            throw new RequestProcessingException(
                    "User with username " + username + " is not authorized to delete workout with id: " + workoutId);
        }
        workoutRepository.delete(existingWorkout);
    }

    @Override
    public List<Exercise> getExercises(String workoutId) {
        if (!workoutRepository.existsById(workoutId)) {
            log.error("{}{}", WORKOUT_NOT_FOUND_ERROR, workoutId);
            throw new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + workoutId);
        }
        log.info("Fetching exercises for workout with id: {}", workoutId);
        Workout workout = workoutRepository.findById(workoutId).get();
        return workout.getExercises();
    }

    @Override
    public Exercise addExercise(String username, String workoutId, String exerciseId) {
        if (!userRepository.existsByEmail(username)) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND + username);
            
        }
        User user = userRepository.findByEmail(username).get();
        Workout workoutExists = user.getWorkouts().stream().filter(workout -> workout.getId().equals(workoutId)).findFirst()
        .orElseThrow(() -> new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + workoutId));

        if (!exerciseRepository.existsById(exerciseId)) {
            log.error(EXERCISE_NOT_FOUND_ERROR, exerciseId);
            throw new ExerciseNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }

        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
        if(!exercise.isPresent()) {
            log.error("Exercise not found with id: {}", exerciseId);
            throw new ExerciseNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        if(exercise.get().getWorkouts()==null) {
            exercise.get().setWorkouts(new ArrayList<>());
        }
        exercise.get().getWorkouts().add(workoutExists);
        exerciseRepository.save(exercise.get());

        log.info("Adding exercise to workout with id: {}", workoutId);
        if(workoutExists.getExercises()==null) {
            workoutExists.setExercises(new ArrayList<>());
        }
        workoutExists.getExercises().add(exercise.get());
        workoutExists.setUpdatedAt(LocalDateTime.now());
        workoutRepository.save(workoutExists);
        return exercise.get();
    }

    @Override
    public Exercise getExercise(String username, String workoutId, String exerciseId) {

        if (!userRepository.existsByEmail(username)) {
            log.error(USER_NOT_FOUND_ERROR, username);
            throw new UserNotFoundException(USER_NOT_FOUND + username);
        }
        User user = userRepository.findByEmail(username).get();
        Workout workout = user.getWorkouts().stream().filter(w -> w.getId().equals(workoutId)).findFirst()
                .orElseThrow(() -> new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + workoutId));
        Exercise exercise = workout.getExercises().stream().filter(e -> e.getId().equals(exerciseId)).findFirst().get();
        if (exercise == null) {
            log.error("Exercise not found with id: {}", exerciseId);
            throw new ExerciseNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId);
        }
        log.info("Fetching exercise with id: {} for workout with id: {}", exerciseId, workoutId);
        return exercise;
    }

    @Override
    public Exercise updateExercise(String username, String workoutId, String exerciseId, Exercise exercise) {
        if (!workoutRepository.existsById(workoutId)) {
            log.error("Workout not found with id: {}", workoutId);
            throw new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + workoutId);
        }
        log.info("Updating exercise with id: {} for workout with id: {}", exerciseId, workoutId);
        Workout workout = workoutRepository.findById(workoutId).get();
        Exercise existingExercise = workout.getExercises().stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ExerciseNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId));
        workout.getExercises().remove(existingExercise);
        if (exercise.getName() != null) {
            existingExercise.setName(exercise.getName());
        }
        if (exercise.getDescription() != null) {
            existingExercise.setDescription(exercise.getDescription());
        }
        if (exercise.getMuscleGroup() != null) {
            existingExercise.setMuscleGroup(exercise.getMuscleGroup());
        }
        if (exercise.getEquipment() != null) {
            existingExercise.setEquipment(exercise.getEquipment());
        }
        if (exercise.getVideoUrl() != null) {
            existingExercise.setVideoUrl(exercise.getVideoUrl());
        }
        if (exercise.getSets() != 0) {
            existingExercise.setSets(exercise.getSets());
        }
        if (exercise.getReps() != 0) {
            existingExercise.setReps(exercise.getReps());
        }
        if (exercise.getRestTime() != null) {
            existingExercise.setRestTime(exercise.getRestTime().toMillis());
        }
        workout.getExercises().add(existingExercise);
        workout.setUpdatedAt(LocalDateTime.now());
        workoutRepository.save(workout);
        return existingExercise;
    }

    @Override
    public void deleteExercise(String id, String exerciseId) {
        if (!workoutRepository.existsById(id)) {
            log.error("{} {}", WORKOUT_NOT_FOUND_ERROR, id);
            throw new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id);
        }
        log.info("Deleting exercise with id: {} for workout with id: {}", exerciseId, id);
        Workout workout = workoutRepository.findById(id).get();
        Exercise existingExercise = workout.getExercises().stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ExerciseNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId));
        workout.getExercises().remove(existingExercise);
        workoutRepository.save(workout);
    }

}
