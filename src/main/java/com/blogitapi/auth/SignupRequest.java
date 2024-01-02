package com.blogitapi.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {

    @JsonProperty("first_name")
    @NotNull(message = "first_name cannot be null")
    @NotBlank(message = "first name must not be empty")
    private String firstName;

    @JsonProperty("last_name")
    @NotNull(message = "last_name cannot be null")
    @NotBlank(message = "last name must not be empty")
    private String lastName;

    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email must not be empty")
    @Email(message = "Invalid email!")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password must not be empty")
    private String password;

}
