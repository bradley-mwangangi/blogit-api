package com.blogitapi.blog.article;

import com.blogitapi.AuditableBlogitEntity;
import com.blogitapi.actors.author.Author;
import com.blogitapi.blog.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "article")
@JsonPropertyOrder({
        "article_id", "title", "content", "tags",
        "author",
        "createdAt", "updatedAt"
})
public class Article implements AuditableBlogitEntity {

    @Id
    @Column(nullable = false)
    @JsonProperty("article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column(nullable = false)
    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title must not be empty")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "content cannot be null")
    @NotBlank(message = "content must not be empty")
    private String content;

    @Column(name = "tag")
    @NotNull(message = "tag cannot be null")
    @ElementCollection
    @CollectionTable(name = "article_tags", joinColumns = @JoinColumn(name = "article_id"))
    private Set<String> tags = new HashSet<>();

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    @ManyToOne( optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Author author;
    // TODO - make author return only author fields
    //  calling author here returns author fields plus user fields since Author is child to AppUser

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "article")
    private List<Comment> commentList;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdTime=" + createdAt +
                ", lastUpdatedTime=" + updatedAt +
                '}';
    }

}
