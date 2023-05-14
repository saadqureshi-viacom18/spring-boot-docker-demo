package com.example.springbootdocker.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Comment {
    private String id;
    private String userId;
    private String message;
}
