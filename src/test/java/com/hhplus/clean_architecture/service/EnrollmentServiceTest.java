package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.EnrollmentListResponse;
import com.hhplus.clean_architecture.entity.Enrollment;
import com.hhplus.clean_architecture.entity.Lecture;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.exception.LectureFullException;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private LectureTimeRepository lectureTimeRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void 특강신청인원_30명_초과_케이스() {

        String userId = "userA";
        long lectureTimeId = 1L;
        String expectedErrorString = "E500";

        LectureTime lectureTime = new LectureTime(1L, "tdd_basic", 30, LocalDateTime.of(2024, 10, 5, 10, 0), false);

        when(lectureTimeRepository.findByIdWithLock(lectureTimeId)).thenReturn(Optional.of(lectureTime));
        when(enrollmentRepository.countByLectureTimeId(lectureTimeId)).thenReturn(30L);

        LectureFullException exception = assertThrows(LectureFullException.class, () ->
                enrollmentService.enroll(userId, lectureTimeId));

        assertEquals(expectedErrorString, exception.getErrorString());
    }

    @Test
    void 특정유저_특강_신청_성공_케이스() {
        // 특강 신청 정원이 30명 미만일 때, 해당 유저가 이 특강에 처음 신청

        String userId = "userA";
        long lectureTimeId = 1L;

        LectureTime lectureTime = new LectureTime(1L, "tdd_basic", 30, LocalDateTime.of(2024, 10, 5, 10, 0), false);
        Enrollment enrollment = Enrollment.builder()
                .userId(userId)
                .lectureTimeId(lectureTimeId)
                .build();

        when(lectureTimeRepository.findByIdWithLock(lectureTimeId)).thenReturn(Optional.of(lectureTime));
        when(enrollmentRepository.countByLectureTimeId(lectureTimeId)).thenReturn(29L);
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        boolean result = enrollmentService.enroll(userId, lectureTime.getId());

        assertTrue(result);
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(enrollmentRepository).countByLectureTimeId(lectureTimeId);
    }

    @Test
    void 한유저의_신청완료된_특강_목록이_없는_케이스() {

        String userId = "userA";

        when(enrollmentRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        List<EnrollmentListResponse> enrolledLectures = enrollmentService.getEnrolledLectures(userId);

        assertNotNull(enrolledLectures);
        assertTrue(enrolledLectures.isEmpty());

        verify(enrollmentRepository, times(1)).findByUserId(userId);
    }

    @Test
    void 특강신청_완료목록_조회_성공_케이스() {

        String userId = "userA";

        Lecture lecture = new Lecture("clean_architecture", "클린 아키텍처", "로이");
        LectureTime lectureTime = new LectureTime(1L, "clean_architecture", 30, LocalDateTime.of(2024, 10, 5, 14, 0), false);
        Enrollment enrollment = new Enrollment(1L, userId, 1L);

        when(enrollmentRepository.findByUserId(userId)).thenReturn(List.of(enrollment));
        when(lectureTimeRepository.findByIdIn(List.of(1L))).thenReturn(List.of(lectureTime));
        when(lectureRepository.findAll()).thenReturn(List.of(lecture));

        List<EnrollmentListResponse> enrolledLectures = enrollmentService.getEnrolledLectures(userId);

        assertNotNull(enrolledLectures);
        assertFalse(enrolledLectures.isEmpty());
        assertEquals(1, enrolledLectures.size());

        EnrollmentListResponse response = enrolledLectures.get(0);
        assertEquals("클린 아키텍처", response.getLectureName());
        assertEquals("로이", response.getTeacherName());
        assertEquals(lectureTime.getLectureTime(), response.getLectureTime());

        verify(enrollmentRepository, times(1)).findByUserId(userId);
        verify(lectureTimeRepository, times(1)).findByIdIn(List.of(1L));
        verify(lectureRepository, times(1)).findAll();
    }
}