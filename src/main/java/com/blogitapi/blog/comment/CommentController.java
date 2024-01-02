package com.blogitapi.blog.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post/{articleId}/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping()
    public ResponseEntity<List<Comment>> getAllCommentsByArticle(@PathVariable("articleId") Long articleId) {
        return new ResponseEntity<>(commentService.getAllCommentsByArticle(articleId), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentByCommentId(@PathVariable("commentId") Long commentId) {
        Comment foundComment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(foundComment, HttpStatus.OK);
    }

    @PostMapping("/create-comment")
    public ResponseEntity<Comment> createComment(
            @PathVariable("articleId") Long articleId,
            @RequestBody Comment comment

    ) {
        return new ResponseEntity<>(commentService.createComment(articleId, comment), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestBody Comment updatedComment
    ) {
        Comment thisComment = commentService.updateComment(commentId, updatedComment);
        return new ResponseEntity<>(thisComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

}
