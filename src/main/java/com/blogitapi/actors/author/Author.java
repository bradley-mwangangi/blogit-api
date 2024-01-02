package com.blogitapi.actors.author;

import com.blogitapi.AuditableBlogitEntity;
import com.blogitapi.actors.appUser.AppUser;
import com.blogitapi.blog.article.Article;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "u_author")
@JsonPropertyOrder({"biography", "createdAt", "updatedAt"})
public class Author extends AppUser implements AuditableBlogitEntity {

    @Column(nullable = false)
    @JsonProperty("biography")
    private String biography;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author")
    private List<Article> articleList;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("createdAt")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime authorCreatedAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonProperty("updatedAt")
    @Column(name = "updated_at")
    private LocalDateTime authorUpdatedAt;

}
