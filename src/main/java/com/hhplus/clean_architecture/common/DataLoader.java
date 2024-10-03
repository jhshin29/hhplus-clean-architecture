package com.hhplus.clean_architecture.common;

import com.hhplus.clean_architecture.entity.Enrollment;
import com.hhplus.clean_architecture.entity.Lecture;
import com.hhplus.clean_architecture.entity.LectureTime;
import com.hhplus.clean_architecture.entity.Users;
import com.hhplus.clean_architecture.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.repository.LectureRepository;
import com.hhplus.clean_architecture.repository.LectureTimeRepository;
import com.hhplus.clean_architecture.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;
    private final LectureTimeRepository lectureTimeRepository;

    @Transactional
    @Bean
    CommandLineRunner loadData() {
        return args -> {
            // UserRepository에 데이터 삽입
            Users user1 = new Users("user1", "유저1");
            Users user2 = new Users("user2", "유저2");
            userRepository.save(user1);
            userRepository.save(user2);

            // LectureRepository에 데이터 삽입
            Lecture lecture1 = new Lecture("tdd_basic", "TDD 101", "로이");
            Lecture lecture2 = new Lecture("clean_architecture", "클린 아키텍처", "허재");
            lectureRepository.save(lecture1);
            lectureRepository.save(lecture2);

            // LectureTimeRepository에 데이터 삽입
            LectureTime lectureTime1 = new LectureTime(null, lecture1.getLectureId(), 30, LocalDateTime.of(2024, 10, 5, 10, 0));
            LectureTime lectureTime2 = new LectureTime(null, lecture2.getLectureId(), 30, LocalDateTime.of(2024, 10, 5, 12, 0));
            lectureTimeRepository.save(lectureTime1);
            lectureTimeRepository.save(lectureTime2);
        };
    }
}
