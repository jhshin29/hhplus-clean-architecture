package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.dto.response.LectureTimeListResponse;
import com.hhplus.clean_architecture.entity.Lecture;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureTimeRepository lectureTimeRepository;
    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional(readOnly = true)
    public List<LectureTimeListResponse> getLectureTimeListByDate(LocalDate date) {

        List<LectureTime> lectureTimes = lectureTimeRepository.findByLectureTimeDate(date);
        if (lectureTimes == null) {
            lectureTimes = Collections.emptyList();
        }
        Map<String, Lecture> lectureMap = lectureRepository.findAll().stream()
                .collect(Collectors.toMap(Lecture::getLectureId, lecture -> lecture));

        return lectureTimes.stream()
                .filter(lectureTime -> {
                    long currentEnrollments = enrollmentRepository.countByLectureTimeId(lectureTime.getId());
                    return currentEnrollments < 30;
                })
                .map(lectureTime -> LectureTimeListResponse.builder()
                        .lectureId(lectureTime.getLectureId())
                        .lectureName(lectureMap.get(lectureTime.getLectureId()).getLectureName())
                        .capacity(lectureTime.getCapacity())
                        .lectureTime(lectureTime.getLectureTime())
                        .build()
                ).toList();
    }
}
