package com.example.prj2.board.dto;

import lombok.Data;

@Data
public class BoardForm {
    private Integer id;
    private String title;
    private String content;
    private String writer;
}
