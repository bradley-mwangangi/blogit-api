package com.blogitapi.actors.moderator;

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
@Table(name = "u_moderator")
@JsonPropertyOrder({"createdAt", "updatedAt"})
public class Moderator extends AppUser implements AuditableBlogitEntity {

    // moderator fields


    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("createdAt")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime moderatorCreatedAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("updatedAt")
    @Column(name = "updated_at")
    private LocalDateTime moderatorUpdatedAt;

}
