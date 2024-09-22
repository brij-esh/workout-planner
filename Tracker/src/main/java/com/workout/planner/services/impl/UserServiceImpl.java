package com.workout.planner.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.workout.planner.dtos.UserRequestDTO;
import com.workout.planner.dtos.UserResponseDTO;
import com.workout.planner.exception.UserAlreadyExistsException;
import com.workout.planner.exception.UserNotFoundException;
import com.workout.planner.exception.WorkoutNotFoundException;
import com.workout.planner.models.Exercise;
import com.workout.planner.models.User;
import com.workout.planner.models.Workout;
import com.workout.planner.repositories.ExerciseRepository;
import com.workout.planner.repositories.UserRepository;
import com.workout.planner.repositories.WorkoutRepository;
import com.workout.planner.services.UserService;
import com.workout.planner.utils.MapperUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private static final String USER_NOT_FOUND_ERROR = "User not found with id: ";
    private final ModelMapper modelMapper;

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            log.error("No users found");
            throw new UserNotFoundException("No users found");
            
        }
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(String id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR + id));
    }

    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error("Email {} is already in use", user.getEmail());
            throw new UserAlreadyExistsException("Email already in use: " + user.getEmail());
        }
        log.info("Creating new user: {}", user);
        User newUser =MapperUtils.map(user, User.class);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser = userRepository.save(newUser);
        return MapperUtils.map(newUser, UserResponseDTO.class);
    }

    @Transactional
    @Override
    public void deleteUser(String username) {
        if (!userRepository.existsByUsername(username)) {
            log.error("User not found with username: {}", username);
            throw new UserNotFoundException("User not found with username: " + username);
        }
        log.info("Deleting user with username: {}", username);
        userRepository.deleteByUsername(username);
    }

    @Transactional
    @Override
    public UserResponseDTO updateUser(String username, UserRequestDTO userDetails) {
        User existingUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR + username));
        log.info("Updating user with username: {}", username);
        
        if (userDetails.getFirstName() != null) {
            existingUser.setFirstName(userDetails.getFirstName());
        }
        
        if (userDetails.getLastName() != null) {
            existingUser.setLastName(userDetails.getLastName());
        }
        
        if (userDetails.getEmail() != null) {
            existingUser.setEmail(userDetails.getEmail());
        }
        
        if (userDetails.getPassword() != null) {
            existingUser.setPassword(userDetails.getPassword());
        }
        if(userDetails.getAge() != 0){
            existingUser.setAge(userDetails.getAge());
        }
        if(userDetails.getPhone() != null){
            existingUser.setPhone(userDetails.getPhone());
        }
        if(userDetails.getGender() != null){
            existingUser.setGender(userDetails.getGender());
        }
        if(!Objects.equals(userDetails.getAge(), null)){
            existingUser.setAge(userDetails.getAge());
        }
        if(userDetails.getWeight() != null){
            existingUser.setWeight(userDetails.getWeight());
        }
        if(userDetails.getHeight() != null){
            existingUser.setHeight(userDetails.getHeight());
        }
        if(userDetails.getGoal() != null){
            existingUser.setGoal(userDetails.getGoal());
        }
        if(userDetails.getProfilePicture() != null){
            existingUser.setProfilePicture(userDetails.getProfilePicture());
        }
        
        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser = userRepository.save(existingUser);
        log.info("User updated: {}", existingUser);
        return MapperUtils.map(existingUser, UserResponseDTO.class);
    }

    

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        log.info("Fetching user with username: {}", username);
        User user1 = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        log.info("User: {}",user1.getCreatedWorkouts().size());
        return userRepository.findByUsername(username)
                        .map(user -> modelMapper.map(user, UserResponseDTO.class))
                        .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    public UserResponseDTO addWorkoutToUsersWorkoutList(String username, String workoutId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new WorkoutNotFoundException("Workout not found with id: " + workoutId));
       
        user.getWorkouts().add(workout);
        workout.getParticipants().add(user);
        workoutRepository.save(workout);
        user.setUpdatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        return modelMapper.map(user, UserResponseDTO.class);
    }
}
