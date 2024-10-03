package com.hhplus.clean_architecture.infrastructure.repository;

import com.hhplus.clean_architecture.domain.entity.LectureTime;
import com.hhplus.clean_architecture.domain.repository.LectureTimeRepository;
import com.hhplus.clean_architecture.infrastructure.persistence.LectureTimeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureTimeRepositoryImpl implements LectureTimeRepository {

    private final LectureTimeJpaRepository lectureTimeJpaRepository;

    @Override
    public List<LectureTime> findByLectureTimeDate(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return lectureTimeJpaRepository.findByLectureTimeDate(startOfDay, endOfDay);
    }

    @Override
    public List<LectureTime> findByIdIn(List<Long> ids) {
        return lectureTimeJpaRepository.findByIdIn(ids);
    }

    @Override
    public Optional<LectureTime> findByIdWithLock(Long lectureTimeId) {
        return lectureTimeJpaRepository.findByIdWithLock(lectureTimeId);
    }

    @Override
    public Optional<LectureTime> findById(long id) {
        return lectureTimeJpaRepository.findById(id);
    }
}
