package com.hhplus.clean_architecture.entity;

import com.hhplus.clean_architecture.entity.base.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Course extends TimeBaseEntity {

    @Id
    @Column(length = 20, nullable = false)
    private String courseId;

    @Column(length = 50, nullable = false)
    private String courseName;

    @Column(length = 20, nullable = false)
    private String teacherName;

    @Column(nullable = false)
    private int classLimit;

    @Column(nullable = false)
    private LocalDateTime courseTime;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isClosed;
}
