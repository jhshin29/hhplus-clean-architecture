package com.hhplus.clean_architecture.infrastructure.persistence;

import com.hhplus.clean_architecture.domain.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentJpaRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUserId(String userId);

    List<Enrollment> findAllByLectureTimeId(Long lectureTimeId);

    long countByLectureTimeId(long lectureTimeId);
}
