package com.hhplus.clean_architecture.domain.repository;

import com.hhplus.clean_architecture.domain.entity.LectureTime;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureTimeRepository {

    List<LectureTime> findByLectureTimeDate(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<LectureTime> findByIdIn(List<Long> ids);

    Optional<LectureTime> findByIdWithLock(@Param("lectureTimeId") Long lectureTimeId);

    Optional<LectureTime> findById(long id);
}
