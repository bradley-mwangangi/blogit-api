package com.blogitapi.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    @JsonProperty("existing_password")
    @NotNull(message = "existing_password cannot be null")
    @NotBlank(message = "existing password must not be empty")
    private String existingPassword;

    @JsonProperty("new_password")
    @NotNull(message = "new_password cannot be null")
    @NotBlank(message = "new password must not be empty")
    private String newPassword;

}
