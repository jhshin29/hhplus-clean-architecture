package com.hhplus.clean_architecture.controller;

import com.hhplus.clean_architecture.dto.request.EnrollmentLectureRequest;
import com.hhplus.clean_architecture.facade.EnrollmentFacade;
import com.hhplus.clean_architecture.facade.LectureFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class LectureController {

    private final EnrollmentFacade enrollmentFacade;
    private final LectureFacade lectureFacade;

    // 특강 신청
    @PostMapping("/register")
    public ResponseEntity<?> courseEnrollment(@RequestBody EnrollmentLectureRequest request) {
        return ResponseEntity.ok(enrollmentFacade.enroll(request));
    }

    // 날짜별로 현재 신청 가능한 특강 목록을 조회 - 정원 30명 고정
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableCourses(@RequestParam LocalDate date) {
        return ResponseEntity.ok(lectureFacade.searchLectureTimeListByDate(date));
    }

    // 특정 userId로 신청 완료된 특강 목록을 조회 - 특강 ID, 이름, 강연자 정보
    public ResponseEntity<?> getRegisteredCourses(@RequestParam String userId) {
        return ResponseEntity.ok(null);
    }
}
