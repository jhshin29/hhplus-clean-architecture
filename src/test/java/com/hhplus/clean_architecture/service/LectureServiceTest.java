package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.LectureTimeListResponse;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureTimeRepository lectureTimeRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private LectureService lectureService;

    @Test
    void 날짜에_신청가능한_특강목록_없는_케이스() {
        LocalDate date = LocalDate.of(2024, 10, 1);

        when(lectureTimeRepository.findByLectureTimeDate(date)).thenReturn(Collections.emptyList());

        List<LectureTimeListResponse> lectureTimeList = lectureService.getLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertTrue(lectureTimeList.isEmpty());
    }

    @Test
    void 날짜의_특강목록중_마감된특강_존재_케이스() {

        LocalDate date = LocalDate.of(2024, 10, 6);

        LectureTime closedLecture = new LectureTime(1L, "server", 30, LocalDateTime.of(2024, 10, 6, 14, 0));
        LectureTime openLecture = new LectureTime(2L, "tdd_basic", 30, LocalDateTime.of(2024, 10, 6, 10, 0));

        when(lectureTimeRepository.findByLectureTimeDate(date)).thenReturn(List.of(closedLecture, openLecture));

        when(enrollmentRepository.countByLectureTimeId(closedLecture.getId())).thenReturn(30L); // 마감된 강의
        when(enrollmentRepository.countByLectureTimeId(openLecture.getId())).thenReturn(20L);

        List<LectureTimeListResponse> lectureTimeList = lectureService.getLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertEquals(1, lectureTimeList.size());
        assertEquals("tdd_basic", lectureTimeList.get(0).getLectureId());
    }

    @Test
    void 해당날짜에_신청가능한_특강목록_조회_성공_케이스() {
        LocalDate date = LocalDate.of(2024, 10, 6);

        LectureTime openLecture = new LectureTime(1L, "tdd_basic", 30, LocalDateTime.of(2024, 10, 6, 10, 0));

        when(lectureTimeRepository.findByLectureTimeDate(date)).thenReturn(List.of(openLecture));
        when(enrollmentRepository.countByLectureTimeId(openLecture.getId())).thenReturn(20L);

        List<LectureTimeListResponse> lectureTimeList = lectureService.getLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertEquals(1, lectureTimeList.size());
        assertEquals("tdd_basic", lectureTimeList.get(0).getLectureId());
    }

}