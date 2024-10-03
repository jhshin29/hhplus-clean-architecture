package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.LectureTimeListResponse;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.repository.LectureRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureTimeRepository lectureTimeRepository;

    @Transactional(readOnly = true)
    public List<LectureTimeListResponse> getLectureTimeListByDate(LocalDate date) {

        List<LectureTime> lectureTimes = lectureTimeRepository.findByLectureTimeDate(date);
        if (lectureTimes == null) {
            lectureTimes = Collections.emptyList();
        }

        return lectureTimes.stream()
                .filter(lectureTime -> !lectureTime.isClosed())
                .map(lectureTime -> LectureTimeListResponse.builder()
                        .lectureId(lectureTime.getLectureId())
                        .capacity(lectureTime.getCapacity())
                        .lectureTime(lectureTime.getLectureTime())
                        .isClosed(lectureTime.isClosed())
                        .build()
                ).toList();
    }
}
