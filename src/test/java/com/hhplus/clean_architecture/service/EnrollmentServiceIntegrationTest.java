package com.hhplus.clean_architecture.service;

import com.hhplus.clean_architecture.domain.entity.Enrollment;
import com.hhplus.clean_architecture.domain.entity.LectureTime;
import com.hhplus.clean_architecture.domain.exception.LectureFullException;
import com.hhplus.clean_architecture.domain.repository.EnrollmentRepository;
import com.hhplus.clean_architecture.domain.repository.LectureTimeRepository;
import com.hhplus.clean_architecture.domain.service.EnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
public class EnrollmentServiceIntegrationTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private LectureTimeRepository lectureTimeRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private LectureTime lectureTime;

    @Test
    @Transactional
    public void 동시에_40명이_특강에_신청시_30명만_성공하는_케이스() throws InterruptedException {
        int totalThreads = 40;

        lectureTime = lectureTimeRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("특강 시간을 찾을 수 없습니다."));

        ExecutorService executorService = Executors.newFixedThreadPool(totalThreads);
        CountDownLatch latch = new CountDownLatch(totalThreads);

        for (int i = 0; i < totalThreads; i++) {
            String userId = "user" + (i + 1);

            executorService.submit(() -> {
                try {
                    enrollmentService.enroll(userId, lectureTime.getId());
                } catch (LectureFullException e) {
                    System.out.println("강의 정원 초과: " + userId);
                } catch (Exception e) {
                    fail("스레드 실행 중 예외 발생: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);

        if (!terminated) {
            fail("일부 스레드가 제대로 종료되지 않았습니다.");
        }

        List<Enrollment> enrollments = enrollmentRepository.findAllByLectureTimeId(lectureTime.getId());
        assertEquals(30, enrollments.size(), "등록된 인원은 30명이 되어야 합니다.");
    }
}
