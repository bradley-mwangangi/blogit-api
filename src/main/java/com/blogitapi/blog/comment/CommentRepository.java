package com.blogitapi.blog.comment;

import com.blogitapi.blog.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentByCommentId(Long commentId);
    List<Comment> findCommentByArticle(Article article);

}
