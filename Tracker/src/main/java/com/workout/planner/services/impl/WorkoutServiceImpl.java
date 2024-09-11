package com.workout.planner.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.workout.planner.dtos.WorkoutRequestDTO;
import com.workout.planner.exception.ExerciseNotFoundException;
import com.workout.planner.exception.WorkoutAlreadyExistException;
import com.workout.planner.exception.WorkoutNotFoundException;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.Workout;
import com.workout.planner.repositories.WorkoutRepository;
import com.workout.planner.services.WorkoutService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService{

    private final ModelMapper modelMapper;
    

    private final WorkoutRepository workoutRepository;

    private static final String WORKOUT_NOT_FOUND_ERROR = "Workout not found with id: ";
    private static final String EXERCISE_NOT_FOUND_ERROR = "Exercise not found with id: ";
    
    @Override
    public Workout createWorkout(WorkoutRequestDTO workoutRequestDTO) {
        if(workoutRepository.findByName(workoutRequestDTO.getName())!=null) {
            log.error("Workout with name {} already exists", workoutRequestDTO.getName());
            throw new WorkoutAlreadyExistException("Workout with name " + workoutRequestDTO.getName() + " already exists");
        }
        log.info("Creating workout with name: {}", workoutRequestDTO.getName());
        Workout workout = modelMapper.map(workoutRequestDTO, Workout.class);
        workout.setCreatedAt(LocalDateTime.now());
        workout.setUpdatedAt(LocalDateTime.now());
        return workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getWorkouts() {
        log.info("Fetching all workouts");
        List<Workout> workouts = workoutRepository.findAll();
        if(workouts.isEmpty()) {
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
    public Workout updateWorkout(String id, WorkoutRequestDTO workoutRequestDTO) {
        log.info("Updating workout with id: {}", id);
        Workout existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id));
        if(workoutRequestDTO.getName() != null) {
            existingWorkout.setName(workoutRequestDTO.getName());
        }
        if(workoutRequestDTO.getDescription() != null) {
            existingWorkout.setDescription(workoutRequestDTO.getDescription());
        }
        if(workoutRequestDTO.getDuration()!=0) {
            existingWorkout.setDuration(workoutRequestDTO.getDuration());
        }
        if(workoutRequestDTO.getTimeOfDay()!=null) {
            existingWorkout.setTimeOfDay(workoutRequestDTO.getTimeOfDay());
        }
        existingWorkout.setUpdatedAt(LocalDateTime.now());
        return workoutRepository.save(existingWorkout);
    }

    @Override
    public void deleteWorkout(String id) {
        log.info("Deleting workout with id: {}", id);
        Workout existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id));
        workoutRepository.delete(existingWorkout);
    }

    @Override
    public List<Exercise> getExercises(String id) {
        if(!workoutRepository.existsById(id)) {
            log.error("{}{}",WORKOUT_NOT_FOUND_ERROR, id);
            throw new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id);
        }
        log.info("Fetching exercises for workout with id: {}", id);
        Workout workout = workoutRepository.findById(id).get();
        return workout.getExercises();
    }

    @Override
    public Exercise addExercise(String id, Exercise exercise) {
        if(!workoutRepository.existsById(id)) {
            log.error("{}{}", WORKOUT_NOT_FOUND_ERROR, id);
            throw new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id);
        }
        log.info("Adding exercise to workout with id: {}", id);
        Workout workout = workoutRepository.findById(id).get();
        workout.getExercises().add(exercise);
        workout.setUpdatedAt(LocalDateTime.now());
        workoutRepository.save(workout);
        return exercise;
    }

    @Override
    public Exercise getExercise(String id, String exerciseId) {
        if(!workoutRepository.existsById(id)) {
            log.error("{} {}", WORKOUT_NOT_FOUND_ERROR, id);
            throw new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id);
        }
        log.info("Fetching exercise with id: {} for workout with id: {}", exerciseId, id);
        Workout workout = workoutRepository.findById(id).get();
        return workout.getExercises().stream()
                .filter(exercise -> exercise.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ExerciseNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId));
    }

    @Override
    public Exercise updateExercise(String id, String exerciseId, Exercise exercise) {
        if(!workoutRepository.existsById(id)) {
            log.error("Workout not found with id: {}", id);
            throw new WorkoutNotFoundException(WORKOUT_NOT_FOUND_ERROR + id);
        }
        log.info("Updating exercise with id: {} for workout with id: {}", exerciseId, id);
        Workout workout = workoutRepository.findById(id).get();
        Exercise existingExercise = workout.getExercises().stream()
                .filter(e -> e.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new ExerciseNotFoundException(EXERCISE_NOT_FOUND_ERROR + exerciseId));
        workout.getExercises().remove(existingExercise);
        if(exercise.getName() != null) {
            existingExercise.setName(exercise.getName());
        }
        if(exercise.getDescription() != null) {
            existingExercise.setDescription(exercise.getDescription());
        }
        if(exercise.getMuscleGroup() != null) {
            existingExercise.setMuscleGroup(exercise.getMuscleGroup());
        }
        if(exercise.getEquipment() != null) {
            existingExercise.setEquipment(exercise.getEquipment());
        }
        if(exercise.getVideoUrl() != null) {
            existingExercise.setVideoUrl(exercise.getVideoUrl());
        }
        if(exercise.getSets() != 0) {
            existingExercise.setSets(exercise.getSets());
        }
        if(exercise.getReps() != 0) {
            existingExercise.setReps(exercise.getReps());
        }
        if(exercise.getRestTime() != null) {
            existingExercise.setRestTime(exercise.getRestTime().toMillis());
        }
        workout.getExercises().add(existingExercise);
        workout.setUpdatedAt(LocalDateTime.now());
        workoutRepository.save(workout);
        return existingExercise;
    }

    @Override
    public void deleteExercise(String id, String exerciseId) {
        if(!workoutRepository.existsById(id)) {
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
