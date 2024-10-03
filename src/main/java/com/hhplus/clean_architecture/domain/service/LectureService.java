package com.hhplus.clean_architecture.domain.service;

import com.hhplus.clean_architecture.domain.entity.LectureTime;
import com.hhplus.clean_architecture.domain.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.domain.repository.LectureTimeRepository;
import com.hhplus.clean_architecture.infrastructure.persistence.LectureTimeJpaRepository;
import com.hhplus.clean_architecture.interfaces.dto.response.LectureTimeListResponse;
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
                .map(lectureTime -> new LectureTimeListResponse(
                        lectureTime.getLectureId(),
                        lectureTime.getCapacity(),
                        lectureTime.getLectureTime())
                ).toList();
    }
}
