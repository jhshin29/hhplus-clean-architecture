package com.hhplus.clean_architecture.infrastructure.repository;

import com.hhplus.clean_architecture.domain.entity.Enrollment;
import com.hhplus.clean_architecture.domain.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.infrastructure.persistence.EnrollmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final EnrollmentJpaRepository enrollmentJpaRepository;

    @Override
    public List<Enrollment> findByUserId(String userId) {
        return enrollmentJpaRepository.findByUserId(userId);
    }

    @Override
    public List<Enrollment> findAllByLectureTimeId(Long lectureTimeId) {
        return enrollmentJpaRepository.findAllByLectureTimeId(lectureTimeId);
    }

    @Override
    public long countByLectureTimeId(long lectureTimeId) {
        return enrollmentJpaRepository.countByLectureTimeId(lectureTimeId);
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        return enrollmentJpaRepository.save(enrollment);
    }
}
