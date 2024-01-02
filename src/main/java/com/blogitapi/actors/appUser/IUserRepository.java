package com.blogitapi.actors.appUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findUserByUserId(UUID userId);
    Optional<AppUser> findUserByEmail(String email);
    boolean existsByEmail(String email);

}
