package com.hhplus.clean_architecture.infrastructure.repository;

import com.hhplus.clean_architecture.domain.entity.Lecture;
import com.hhplus.clean_architecture.domain.repository.LectureRepository;
import com.hhplus.clean_architecture.infrastructure.persistence.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public List<Lecture> findAll() {
        return lectureJpaRepository.findAll();
    }
}
