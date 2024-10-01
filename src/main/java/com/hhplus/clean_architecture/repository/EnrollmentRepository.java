package com.hhplus.clean_architecture.repository;

import com.hhplus.clean_architecture.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByUserIdAndLectureTimeId(String userId, long courseNameId);
}
