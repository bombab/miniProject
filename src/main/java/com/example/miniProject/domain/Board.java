package com.example.miniProject.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("board") // 테이블 이름 명시
public class Board {
    @Id
    private Long id;

    private String name;
    private String title;
    private String password;
    private String content;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now(); // 기본 생성 시 현재 시간 할당
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now(); // 기본 생성 시 현재 시간 할당 (업데이트 시 갱신 필요)
}