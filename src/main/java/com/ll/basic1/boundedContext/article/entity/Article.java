package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY) // Auto_increment
    private long id;
    @CreatedDate
    private LocalDateTime createDate; // 데이터 생성 날짜
    @LastModifiedDate
    private LocalDateTime modifyDate; // 데이터 수정 날짜
    private String title;
    private String body;
}
