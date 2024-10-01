package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.LectureTimeListResponse;
import com.hhplus.clean_architecture.entity.LectureTime;
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

    @InjectMocks
    private LectureService lectureService;

    @Test
    void 날짜에_신청가능한_특강목록_없는_케이스() {
        LocalDate date = LocalDate.of(2024, 10, 1);

        when(lectureTimeRepository.findByLectureTimeDate(date)).thenReturn(Collections.emptyList());

        List<LectureTimeListResponse> lectureTimeList = lectureService.searchLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertTrue(lectureTimeList.isEmpty());
    }

    @Test
    void 날짜의_특강목록중_마감된특강_존재_케이스() {

        LocalDate date = LocalDate.of(2024, 10, 6);

        LectureTime closedLecture = new LectureTime(1L, "server", 30, 30, LocalDateTime.of(2024, 10, 6, 14, 0), true);
        LectureTime openLecture = new LectureTime(2L, "tdd_basic", 30, 20, LocalDateTime.of(2024, 10, 6, 10, 0), false);

        when(lectureTimeRepository.findByLectureTimeDate(date)).thenReturn(List.of(openLecture));

        List<LectureTimeListResponse> lectureTimeList = lectureService.searchLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertEquals(1, lectureTimeList.size());
        assertTrue(lectureTimeList.stream().noneMatch(LectureTimeListResponse::isClosed));   // 마감되지 않은 특강이 있는지 확인
    }

    @Test
    void 해당날짜에_신청가능한_특강목록_조회_성공_케이스() {
        LocalDate date = LocalDate.of(2024, 10, 6);

        LectureTime openLecture = new LectureTime(1L, "tdd_basic", 30, 20, LocalDateTime.of(2024, 10, 6, 10, 0), false);

        when(lectureTimeRepository.findByLectureTimeDate(date)).thenReturn(List.of(openLecture));

        List<LectureTimeListResponse> lectureTimeList = lectureService.searchLectureTimeListByDate(date);

        assertNotNull(lectureTimeList);
        assertEquals(1, lectureTimeList.size());
        assertFalse(lectureTimeList.get(0).isClosed());
        assertEquals("tdd_basic", lectureTimeList.get(0).getLectureId());
    }

    @Test
    void 한유저의_신청완료된_특강_목록이_없는_케이스() {
        // 빈 목록 반환
    }

    @Test
    void 특강신청_완료목록_조회_성공_케이스() {

    }

}