package com.hhplus.clean_architecture.entity;

import com.hhplus.clean_architecture.entity.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "courseId"})
        }
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Registration extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_id_seq_generator")
    @SequenceGenerator(name = "registration_id_seq_generator", sequenceName = "registration_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 20, nullable = false)
    private String userId;

    @Column(length = 20, nullable = false)
    private String courseId;
}
