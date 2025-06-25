package com.example.prj2.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {
    Integer id;
    String title;
    String content;
    String writer;
    LocalDateTime createdAt;
}
