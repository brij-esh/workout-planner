package com.workout.planner.services;
import java.util.List;

import com.workout.planner.dtos.UserRequestDTO;
import com.workout.planner.dtos.UserResponseDTO;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO createUser(UserRequestDTO user);
    UserResponseDTO getUserById(String id);
    UserResponseDTO updateUser(String id, UserRequestDTO user);
    void deleteUser(String id);
    UserResponseDTO getUserByUsername(String username);
    UserResponseDTO addWorkoutToUsersWorkoutList(String username, String workoutId);
}
