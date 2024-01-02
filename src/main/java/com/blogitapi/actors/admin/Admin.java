package com.blogitapi.actors.admin;

import com.blogitapi.AuditableBlogitEntity;
import com.blogitapi.actors.appUser.AppUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "u_admin")
@JsonPropertyOrder({"createdAt", "updatedAt"})
public class Admin extends AppUser implements AuditableBlogitEntity {

    // admin fields


    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("createdAt")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime adminCreatedAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("updatedAt")
    @Column(name = "updated_at")
    private LocalDateTime adminUpdatedAt;

}
