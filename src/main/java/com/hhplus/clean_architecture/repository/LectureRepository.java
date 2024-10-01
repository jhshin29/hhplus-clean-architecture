package com.hhplus.clean_architecture.repository;

import com.hhplus.clean_architecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, String> {
}
