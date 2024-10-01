package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.entity.Enrollment;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.exception.LectureFullException;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private LectureTimeRepository lectureTimeRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void 특강신청인원_30명_초과_케이스() {

        String userId = "userA";
        long lectureTimeId = 1L;
        String expectedErrorString = "E500";

        LectureTime lectureTime = new LectureTime(1L, "tdd_basic", 30, 30, LocalDateTime.of(2024, 10, 5, 10, 0), false);
        when(lectureTimeRepository.findById(lectureTimeId)).thenReturn(Optional.of(lectureTime));

        LectureFullException exception = assertThrows(LectureFullException.class, () ->
                enrollmentService.enroll(userId, lectureTimeId));

        assertEquals(expectedErrorString, exception.getErrorString());
    }

    @Test
    void 특강신청인원_30명_초과되었으나_마감상태_미변경_케이스() {

    }

    @Test
    void 특정유저_특강_신청_성공_케이스() {
        // 특강 신청 정원이 30명 미만일 때, 해당 유저가 이 특강에 처음 신청

        String userId = "userA";
        long lectureTimeId = 1L;

        LectureTime lectureTime = new LectureTime(1L, "tdd_basic", 30, 28, LocalDateTime.of(2024, 10, 5, 10, 0), false);
        Enrollment enrollment = Enrollment.builder()
                .userId(userId)
                .lectureTimeId(lectureTimeId)
                .build();

        when(lectureTimeRepository.findById(lectureTimeId)).thenReturn(Optional.of(lectureTime));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

        boolean result = enrollmentService.enroll(userId, lectureTimeId);

        assertTrue(result);
        assertEquals(29, lectureTime.getCurrentRegistrations());
        verify(enrollmentRepository).save(any(Enrollment.class));
    }

}