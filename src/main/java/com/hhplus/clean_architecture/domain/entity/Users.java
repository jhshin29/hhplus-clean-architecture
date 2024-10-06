package com.hhplus.clean_architecture.domain.entity;

import com.hhplus.clean_architecture.domain.entity.base.TimeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Users extends TimeBaseEntity {

    @Id
    @Column(length = 20)
    private String userId;

    @Column(length = 20)
    private String name;
}
