package com.hhplus.clean_architecture.infrastructure.persistence;

import com.hhplus.clean_architecture.domain.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, String> {
}
