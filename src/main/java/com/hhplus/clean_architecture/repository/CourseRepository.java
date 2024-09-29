package com.hhplus.clean_architecture.repository;

import com.hhplus.clean_architecture.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
