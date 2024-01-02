package com.blogitapi.blog.comment;

import com.blogitapi.actors.appUser.AppUser;
import com.blogitapi.actors.appUser.IUserRepository;
import com.blogitapi.blog.article.Article;
import com.blogitapi.blog.article.ArticleRepository;
import com.blogitapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final IUserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public CommentService(IUserRepository userRepository, CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    List<Comment> getAllCommentsByArticle(Long articleId) {
        Article article = articleRepository.findArticleByArticleId(articleId)
                .orElseThrow(() -> new NotFoundException("Article", articleId));
        List<Comment> commentList = commentRepository.findCommentByArticle(article);

        return new ArrayList<>(commentList);
    }

    Comment getCommentById(Long commentId) {
        return commentRepository.findCommentByCommentId(commentId)
                .orElseThrow(() -> new NotFoundException("Comment", commentId));
    }

    Comment createComment(Long articleId, Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        AppUser author = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("User", null));
        comment.setUser(author);

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Article", articleId));
        comment.setArticle(article);

        return commentRepository.save(comment);
    }

    Comment updateComment(Long commentId, Comment updatedComment) {
        Comment existingComment = getCommentById(commentId);
        existingComment.setContent(updatedComment.getContent());

        return commentRepository.save(existingComment);
    }

    void deleteComment(Long commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment", commentId);
        }

        commentRepository.deleteById(commentId);
    }

}
