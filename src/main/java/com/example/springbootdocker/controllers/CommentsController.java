package com.example.springbootdocker.controllers;

import com.example.springbootdocker.models.PostCommentRequest;
import com.example.springbootdocker.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @GetMapping("comments")
    public ResponseEntity<Object> getAllComments () {
        return ResponseEntity.ok().body(commentService.getAllComments());
    }

    @GetMapping("users/{userId}/comments")
    public ResponseEntity<Object> getAllCommentsByUser (@PathVariable String userId) {
        return ResponseEntity.ok().body(commentService.getAllCommentsByUser(userId));
    }

    @PostMapping("users/{userId}/comments")
    public ResponseEntity<Object> postComment (@PathVariable String userId, @RequestBody String message) {
        commentService.postComment(userId, message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Object> deleteComment (@PathVariable String commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
