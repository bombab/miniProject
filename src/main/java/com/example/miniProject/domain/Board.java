package com.example.miniProject.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Setter
@Getter
public class Board {
    @Id
    private Long id;

    private String name;
    private String title;
    private String password;
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시 자동으로 시간을 설정합니다.
    private LocalDateTime updatedAt = LocalDateTime.now(); // 생성 시 자동으로 시간을 설정하고, 업데이트 시 갱신 필요
}