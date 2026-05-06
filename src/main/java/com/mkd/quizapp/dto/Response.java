package com.mkd.quizapp.dto;

import lombok.Data;

@Data
public class Response {
    private Integer id;          // question id
    private String response;     // user's selected answer
}