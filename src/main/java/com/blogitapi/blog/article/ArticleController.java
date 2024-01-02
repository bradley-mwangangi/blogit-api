package com.blogitapi.blog.article;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR') OR hasAnyAuthority('admin_read')")
    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAllArticles() {
        return new ResponseEntity<>(articleService.getAllArticles(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/highlights")
    public ResponseEntity<List<Article>> getHighlights() {
        return new ResponseEntity<>(articleService.getHighlights(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{articleId}")
    public ResponseEntity<Article> getArticleById(@PathVariable("articleId") Long articleId) {
        Article foundArticle = articleService.getArticleById(articleId);
        return new ResponseEntity<>(foundArticle, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping("/create-article")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        return new ResponseEntity<>(articleService.createArticle(article), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR') or hasAnyAuthority('admin_update', 'update_article')")
    @PutMapping("/{articleId}")
    public ResponseEntity<Article> updateArticle(
            @PathVariable Long articleId,
            @RequestBody Article articleUpdate
    ) {
        Article updatedArticle = articleService.updateArticle(articleId, articleUpdate);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR') or hasAnyAuthority('admin_delete', 'delete_article')")
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }

}
