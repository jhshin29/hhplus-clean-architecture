package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.EnrollmentListResponse;
import com.hhplus.clean_architecture.entity.Enrollment;
import com.hhplus.clean_architecture.entity.Lecture;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final LectureTimeRepository lectureTimeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;

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

    public List<EnrollmentListResponse> getEnrolledLectures(String userId) {

        List<Enrollment> enrollmentList = enrollmentRepository.findByUserId(userId);

        List<Long> lectureTimeIds = enrollmentList.stream().map(Enrollment::getLectureTimeId).toList();
        List<LectureTime> lectureTimes = lectureTimeRepository.findByIdIn(lectureTimeIds);
        Map<String, Lecture> lectureMap = lectureRepository.findAll().stream()
                .collect(Collectors.toMap(Lecture::getLectureId, lecture -> lecture));

        return lectureTimes.stream().map(lectureTime -> EnrollmentListResponse.builder()
                .lectureId(lectureTime.getLectureId())
                .lectureName(lectureMap.get(lectureTime.getLectureId()).getLectureName())
                .teacherName(lectureMap.get(lectureTime.getLectureId()).getTeacherName())
                .lectureTime(lectureTime.getLectureTime())
                .build()).toList();
    }
}
