package com.example.prj2.board.dto;

import java.time.LocalDateTime;

public interface BoardListInfo {
    public String getId();

    public String getTitle();

    public String getWriter();

    public LocalDateTime getCreatedAt();
}
