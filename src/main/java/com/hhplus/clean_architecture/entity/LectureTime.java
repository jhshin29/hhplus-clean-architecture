package com.hhplus.clean_architecture.entity;

import com.hhplus.clean_architecture.entity.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LectureTime extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String lectureId;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private LocalDateTime lectureTime;
}
