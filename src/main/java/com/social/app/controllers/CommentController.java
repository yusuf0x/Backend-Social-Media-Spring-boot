package com.social.app.controllers;

import com.social.app.models.Comment;
import com.social.app.payload.request.NewCommentRequest;
import com.social.app.payload.response.ApiResponse;
import com.social.app.payload.response.ApiResponseWithData;
import com.social.app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentsService;
    @Autowired
    public CommentController(CommentService commentsService) {
        this.commentsService = commentsService;
    }
    @PostMapping("/add-comment")
    public ResponseEntity<?> addNewComment(@RequestBody NewCommentRequest request) {
        try {
            Comment comment = commentsService.addNewComment(request.getUserId(), request.getPostId(), request.getComment());
            return ResponseEntity.ok(new ApiResponse(true, "New comment Added"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComment() {
        return new ResponseEntity(new ApiResponseWithData<>(true,"All comments",commentsService.getAllComment()),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(commentsService.getCommentById(id), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        Comment existingComment = commentsService.getCommentById(id);
        updatedComment.setId(existingComment.getId());
        return new ResponseEntity<>(commentsService.saveComment(updatedComment),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentsService.deleteComment(id);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentsService.getCommentsByPostId(postId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get Comments By Post Id", comments));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getCommentsByUserId(@PathVariable Long userId) {
        try {
            List<Comment> comments = commentsService.getCommentsByUserId(userId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Get Comments By user Id", comments));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, e.getMessage()));
        }
    }
}
