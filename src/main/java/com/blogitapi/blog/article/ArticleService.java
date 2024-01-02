package com.blogitapi.blog.article;

import com.blogitapi.actors.appUser.IUserRepository;
import com.blogitapi.actors.author.Author;
import com.blogitapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final IUserRepository userRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, IUserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    List<Article> getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        return new ArrayList<>(articleList);
    }

    List<Article> getHighlights() {
        return articleRepository.findFirst10ByOrderByCreatedAt();
    }

    Article getArticleById(Long articleId) {
        return articleRepository.findArticleByArticleId(articleId)
                .orElseThrow(() -> new NotFoundException("Article", articleId));
    }

    Article createArticle(Article article) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Author author = (Author) userRepository.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("User", null));

        article.setCreatedAt(LocalDateTime.now());
        article.setAuthor(author);

        return articleRepository.save(article);
    }

    Article updateArticle(Long articleId, Article updatedArticle) {
        Article existingArticle = getArticleById(articleId);
        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setContent(updatedArticle.getContent());

        return articleRepository.save(existingArticle);
    }

    void deleteArticle(Long articleId) {
        if(!articleRepository.existsById(articleId)) {
            throw new NotFoundException("Article", articleId);
        }

        articleRepository.deleteById(articleId);
    }

}
