package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.EnrollmentListResponse;
import com.hhplus.clean_architecture.entity.Enrollment;
import com.hhplus.clean_architecture.entity.Lecture;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.exception.LectureFullException;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PessimisticLockException;
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

        LectureTime lectureTime = lectureTimeRepository.findByIdWithLock(lectureTimeId)
                .orElseThrow(() -> new EntityNotFoundException("특강 시간을 찾을 수 없습니다."));

        long currentEnrollments = enrollmentRepository.countByLectureTimeId(lectureTimeId);

        if (currentEnrollments >= 30) {
            throw new LectureFullException("강의의 최대 인원에 도달했습니다.");
        }

//        lectureTime.checkAndCloseIfFull(currentEnrollments);
//
//        lectureTimeRepository.saveAndFlush(lectureTime);
//        System.out.println("강의 마감여부 (저장 후): " + lectureTime.isClosed());

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

        return lectureTimes.stream().map(lectureTime -> EnrollmentListResponse.builder()
                .lectureId(lectureTime.getLectureId())
                .lectureName(lectureMap.get(lectureTime.getLectureId()).getLectureName())
                .teacherName(lectureMap.get(lectureTime.getLectureId()).getTeacherName())
                .lectureTime(lectureTime.getLectureTime())
                .build()).toList();
    }
}
