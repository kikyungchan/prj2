package com.example.prj2.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@Table(name = "member")
public class Member {
    @Id
    private String id;
    private String password;
    private String nickName;
    private String info;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
