package com.workout.planner.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.workout.planner.models.User;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

    Optional<User> findByEmail(String email);

}