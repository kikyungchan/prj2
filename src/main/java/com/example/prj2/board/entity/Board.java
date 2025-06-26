package com.example.prj2.board.entity;

import com.example.prj2.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "writer")
    private Member writer;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
