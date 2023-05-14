package com.example.springbootdocker.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCommentRequest {
    private String message;
}
