package com.hhplus.clean_architecture.domain.repository;

import com.hhplus.clean_architecture.domain.entity.Enrollment;

import java.util.List;

public interface EnrollmentRepository {

    List<Enrollment> findByUserId(String userId);

    List<Enrollment> findAllByLectureTimeId(Long lectureTimeId);

    long countByLectureTimeId(long lectureTimeId);

    Enrollment save(Enrollment enrollment);

    boolean existsByUserIdAndLectureTimeId(String userId, long lectureTimeId);
}
