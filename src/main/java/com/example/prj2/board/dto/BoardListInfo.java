package com.example.prj2.board.dto;

import com.example.prj2.member.dto.MemberListinfo;

import java.time.LocalDateTime;

public interface BoardListInfo {
    public String getId();

    public String getTitle();

    public MemberListinfo getWriter();

    public LocalDateTime getCreatedAt();
}
