package com.example.prj2.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    private String id;
    private String nickName;
    private String info;
    private String writer;
    private LocalDateTime createdAt;


}
