package com.hhplus.clean_architecture.entity;

import com.hhplus.clean_architecture.entity.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "lectureTimeId"})
        }
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Enrollment extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String userId;

    @Column(length = 20, nullable = false)
    private Long lectureTimeId;
}
