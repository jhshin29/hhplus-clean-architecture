package com.hhplus.clean_architecture.infrastructure.persistence;

import com.hhplus.clean_architecture.domain.entity.LectureTime;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureTimeJpaRepository extends JpaRepository<LectureTime, Long> {

    @Query("SELECT lt FROM LectureTime lt WHERE lt.lectureTime BETWEEN :startOfDay AND :endOfDay AND " +
            "(SELECT COUNT(e) FROM Enrollment e WHERE e.lectureTimeId = lt.id) < 30")
    List<LectureTime> findByLectureTimeDate(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    List<LectureTime> findByIdIn(List<Long> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select lt from LectureTime lt where lt.id = :lectureTimeId")
    Optional<LectureTime> findByIdWithLock(@Param("lectureTimeId") Long lectureTimeId);
}
