package com.example.springbootdocker.services;

import com.example.springbootdocker.models.Comment;
import com.example.springbootdocker.models.PostCommentRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getAllComments () {
        return new ArrayList<Comment>(comments);
    }
    public List<Comment> getAllCommentsByUser (String userId) {
        return comments.stream()
                .filter(comment -> comment.getUserId().equals(userId))
                .toList();
    }

    public void postComment (String userId, String message) {
        comments.add(Comment.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .message(message)
                .build());
    }

    public void deleteCommentById (String commentId) {
        comments.removeIf(comment -> comment.getId().equals(commentId));
    }
}
