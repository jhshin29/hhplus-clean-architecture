package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.domain.entity.LectureTime;
import com.hhplus.clean_architecture.domain.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.domain.repository.LectureTimeRepository;
import com.hhplus.clean_architecture.domain.service.LectureService;
import com.hhplus.clean_architecture.interfaces.dto.response.LectureTimeListResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureTimeRepository lectureTimeRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    void 날짜에_신청가능한_특강목록_없는_케이스() {
        LocalDate date = LocalDate.of(2024, 10, 1);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        when(lectureTimeRepository.findByLectureTimeDate(startOfDay, endOfDay)).thenReturn(Collections.emptyList());

        List<LectureTimeListResponse> lectureTimeList = lectureService.getLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertTrue(lectureTimeList.isEmpty());
    }

    @Test
    void 날짜의_특강목록중_마감된특강_존재_케이스() {

        LocalDate date = LocalDate.of(2024, 10, 5);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        LectureTime closedLecture = new LectureTime(1L, "tdd_basic", 30, LocalDateTime.of(2024, 10, 5, 12, 0));
        LectureTime openLecture = new LectureTime(2L, "clean_architecture", 30, LocalDateTime.of(2024, 10, 5, 10, 0));

        when(lectureTimeRepository.findByLectureTimeDate(startOfDay, endOfDay)).thenReturn(List.of(closedLecture, openLecture));

        when(enrollmentRepository.countByLectureTimeId(closedLecture.getId())).thenReturn(30L); // 마감된 강의
        when(enrollmentRepository.countByLectureTimeId(openLecture.getId())).thenReturn(20L);

        List<LectureTimeListResponse> lectureTimeList = lectureService.getLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertEquals(1, lectureTimeList.size());
        assertEquals("clean_architecture", lectureTimeList.get(0).lectureId());
    }

    @Test
    void 해당날짜에_신청가능한_특강목록_조회_성공_케이스() {
        LocalDate date = LocalDate.of(2024, 10, 5);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        LectureTime openLecture = new LectureTime(1L, "tdd_basic", 30, LocalDateTime.of(2024, 10, 5, 10, 0));

        when(lectureTimeRepository.findByLectureTimeDate(startOfDay, endOfDay)).thenReturn(List.of(openLecture));
        when(enrollmentRepository.countByLectureTimeId(openLecture.getId())).thenReturn(20L);

        List<LectureTimeListResponse> lectureTimeList = lectureService.getLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertEquals(1, lectureTimeList.size());
        assertEquals("tdd_basic", lectureTimeList.get(0).lectureId());
    }

}