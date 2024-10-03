package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.LectureTimeListResponse;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureTimeRepository lectureTimeRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional(readOnly = true)
    public List<LectureTimeListResponse> getLectureTimeListByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<LectureTime> lectureTimes = lectureTimeRepository.findByLectureTimeDate(startOfDay, endOfDay);
        if (lectureTimes == null) {
            lectureTimes = Collections.emptyList();
        }

        return lectureTimes.stream()
                .filter(lectureTime -> {
                    long currentEnrollments = enrollmentRepository.countByLectureTimeId(lectureTime.getId());
                    return currentEnrollments < 30;
                })
                .map(lectureTime -> LectureTimeListResponse.builder()
                        .lectureId(lectureTime.getLectureId())
                        .capacity(lectureTime.getCapacity())
                        .lectureTime(lectureTime.getLectureTime())
                        .build()
                ).toList();
    }
}
