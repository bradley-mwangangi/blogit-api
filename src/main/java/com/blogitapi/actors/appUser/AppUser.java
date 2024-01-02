package com.blogitapi.actors.appUser;

import com.blogitapi.AuditableBlogitEntity;
import com.blogitapi.actors.role.Role;
import com.blogitapi.actors.admin.Admin;
import com.blogitapi.actors.author.Author;
import com.blogitapi.actors.moderator.Moderator;
import com.blogitapi.blog.comment.Comment;
import com.blogitapi.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "u_appuser")
@JsonPropertyOrder({
        "user_id", "first_name", "last_name", "email", "username",
        "roles", "authorities", "author", "moderator", "admin",
        "createdAt", "updatedAt"
})
public class AppUser implements UserDetails, AuditableBlogitEntity {

    @Id
    @Column(nullable = false)
    @JsonProperty("user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(nullable = false)
    @JsonProperty("first_name")
    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name must not be empty")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last name must not be empty")
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email!")
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email must not be empty")
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "Password must not be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private List<Token> tokenList;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public boolean isAuthor() {
        return this instanceof Author;
    }

    public boolean isModerator() {
        return this instanceof Moderator;
    }

    public boolean isAdmin() {
        return this instanceof Admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.addAll(role.getAuthorities());
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser appUser)) return false;
        return Objects.equals(userId, appUser.userId)
                && Objects.equals(firstName, appUser.firstName)
                && Objects.equals(lastName, appUser.lastName)
                && Objects.equals(email, appUser.email)
                && Objects.equals(roles, appUser.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                userId,
                firstName,
                lastName,
                email,
                roles
        );
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }

}
