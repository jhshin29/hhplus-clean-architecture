package com.hhplus.clean_architecture.repository;

import com.hhplus.clean_architecture.entity.LectureTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LectureTimeRepository extends JpaRepository<LectureTime, Long> {

    @Query("select lt from LectureTime lt where date(lt.lectureTime) = :date and lt.isClosed = false")
    List<LectureTime> findByLectureTimeDate(@Param("date") LocalDate date);
}
