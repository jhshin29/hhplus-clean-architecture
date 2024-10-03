package com.hhplus.clean_architecture.entity;

import com.hhplus.clean_architecture.entity.base.TimeBaseEntity;
import com.hhplus.clean_architecture.exception.LectureFullException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LectureTime extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_time_id_seq_generator")
    @SequenceGenerator(name = "course_time_id_seq_generator", sequenceName = "course_time_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 20, nullable = false)
    private String lectureId;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private LocalDateTime lectureTime;
}
