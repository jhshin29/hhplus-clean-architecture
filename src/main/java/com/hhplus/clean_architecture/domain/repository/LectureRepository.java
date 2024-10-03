package com.hhplus.clean_architecture.domain.repository;

import com.hhplus.clean_architecture.domain.entity.Lecture;

import java.util.List;

public interface LectureRepository {

    List<Lecture> findAll();
}
