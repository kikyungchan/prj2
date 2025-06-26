package com.example.prj2.board.dto;

import com.example.prj2.member.dto.MemberDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {
    Integer id;
    String title;
    String content;
    MemberDto writer;
    LocalDateTime createdAt;
}
