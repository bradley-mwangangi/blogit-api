package com.blogitapi.actors.appUser;

import com.blogitapi.auth.ChangePasswordRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.security.Principal;
import java.util.UUID;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AppUser> getUserById(@PathVariable("userId") UUID userId) {
        AppUser foundUser = userService.getUserById(userId);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<AppUser> getUserProfile() {
        AppUser foundUser = userService.getAuthenticatedUser();
        if (foundUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            Principal connectedUser
    ) {
        try {
            userService.changePassword(changePasswordRequest, connectedUser);
        } catch (CredentialException e) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            return new ResponseEntity<>(problemDetails, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

}
