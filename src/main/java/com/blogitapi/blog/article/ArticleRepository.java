package com.blogitapi.blog.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findFirst10ByOrderByCreatedAt();
    Optional<Article> findArticleByArticleId(Long articleId);

}
