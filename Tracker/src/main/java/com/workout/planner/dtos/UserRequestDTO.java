package com.workout.planner.dtos;
import java.sql.Blob;

import com.workout.planner.enums.Gender;
import com.workout.planner.validations.CreateGroup;
import com.workout.planner.validations.UpdateGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Username is required", groups = {CreateGroup.class})
    private String username;

    @NotBlank(message = "Email is required", groups = {CreateGroup.class})
    @Email(message = "Invalid email format", groups = {CreateGroup.class, UpdateGroup.class})
    private String email;

    @NotBlank(message = "Password is required", groups = {CreateGroup.class})
    @Size(min = 6, message = "Password must be at least 6 characters long", groups = {CreateGroup.class, UpdateGroup.class})
    private String password;

    @NotBlank(message = "First name is required", groups = {CreateGroup.class})
    private String firstName;

    @NotBlank(message = "Last name is required", groups = {CreateGroup.class})
    private String lastName;

    @NotBlank(message = "Phone is required", groups = {CreateGroup.class})
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number format", groups = {CreateGroup.class, UpdateGroup.class})
    private String phone;

    @NotBlank(message = "Gender is required", groups = {CreateGroup.class})
    private Gender gender;

    @NotBlank(message = "Age is required", groups = {CreateGroup.class})
    private int age;

    private Double weight;

    private Double height;

    private String goal;

    private Blob profilePicture;
}
