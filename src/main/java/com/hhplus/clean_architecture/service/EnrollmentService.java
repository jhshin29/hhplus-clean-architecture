package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.entity.Enrollment;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final LectureTimeRepository lectureTimeRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean enroll(String userId, long lectureTimeId) {
        LectureTime lectureTime = lectureTimeRepository.findById(lectureTimeId)
                .orElseThrow(() -> new EntityNotFoundException("특강 시간을 찾을 수 없습니다."));

        // 내가 선택한 코스가 30명이 넘었는지 lectureTime 내부에서 확인
        lectureTime.addRegistrations();

        Enrollment newEnrollment = Enrollment.builder()
                .id(null)
                .userId(userId)
                .lectureTimeId(lectureTimeId)
                .build();

        enrollmentRepository.save(newEnrollment);

        return true;
    }

}
