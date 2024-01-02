package com.blogitapi.actors.appUser;

import com.blogitapi.auth.ChangePasswordRequest;

import javax.security.auth.login.CredentialException;
import java.security.Principal;
import java.util.UUID;

public interface IUserService {

    AppUser getUserById(UUID userId);
    void changePassword(ChangePasswordRequest request, Principal connectedUser) throws CredentialException;

}
