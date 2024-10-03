package com.hhplus.clean_architecture.domain.service;

import com.hhplus.clean_architecture.domain.entity.Enrollment;
import com.hhplus.clean_architecture.domain.entity.Lecture;
import com.hhplus.clean_architecture.domain.entity.LectureTime;
import com.hhplus.clean_architecture.domain.exception.LectureFullException;
import com.hhplus.clean_architecture.domain.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.domain.repository.LectureRepository;
import com.hhplus.clean_architecture.domain.repository.LectureTimeRepository;
import com.hhplus.clean_architecture.interfaces.dto.response.EnrollmentListResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean enroll(String userId, long lectureTimeId) {

        lectureTimeRepository.findByIdWithLock(lectureTimeId)
                .orElseThrow(() -> new EntityNotFoundException("특강 시간을 찾을 수 없습니다."));

        boolean isAlreadyEnrolled = enrollmentRepository.existsByUserIdAndLectureTimeId(userId, lectureTimeId);
        if (isAlreadyEnrolled) {
            throw new IllegalStateException("해당 특강에 이미 신청하셨습니다.");
        }

        long currentEnrollments = enrollmentRepository.countByLectureTimeId(lectureTimeId);

        if (currentEnrollments >= 30) {
            throw new LectureFullException("강의의 최대 인원에 도달했습니다.");
        }

        Enrollment newEnrollment = Enrollment.builder()
                .id(null)
                .userId(userId)
                .lectureTimeId(lectureTimeId)
                .build();

        System.out.println("User " + newEnrollment.getUserId() + " 등록 완료.");
        enrollmentRepository.save(newEnrollment);

        return true;
    }

    public List<EnrollmentListResponse> getEnrolledLectures(String userId) {

        List<Enrollment> enrollmentList = enrollmentRepository.findByUserId(userId);

        List<Long> lectureTimeIds = enrollmentList.stream().map(Enrollment::getLectureTimeId).toList();
        List<LectureTime> lectureTimes = lectureTimeRepository.findByIdIn(lectureTimeIds);
        Map<String, Lecture> lectureMap = lectureRepository.findAll().stream()
                .collect(Collectors.toMap(Lecture::getLectureId, lecture -> lecture));

        return lectureTimes.stream().map(lectureTime ->
                        new EnrollmentListResponse(
                                lectureTime.getLectureId(),
                                lectureMap.get(lectureTime.getLectureId()).getLectureName(),
                                lectureMap.get(lectureTime.getLectureId()).getTeacherName(),
                                lectureTime.getLectureTime()
                        )
                ).toList();
    }
}
