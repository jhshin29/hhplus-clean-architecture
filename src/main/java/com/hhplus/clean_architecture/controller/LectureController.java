package com.hhplus.clean_architecture.controller;

import com.hhplus.clean_architecture.dto.request.EnrollmentLectureRequest;
import com.hhplus.clean_architecture.dto.response.EnrollmentListResponse;
import com.hhplus.clean_architecture.facade.LectureFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {

    private final LectureFacade lectureFacade;

    // 특강 신청
    @PostMapping("/register")
    public ResponseEntity<?> courseEnrollment(@RequestBody EnrollmentLectureRequest request) {
        return ResponseEntity.ok(lectureFacade.enroll(request));
    }

    // 날짜별로 현재 신청 가능한 특강 목록을 조회 - 정원 30명 고정
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableLectures(@RequestParam("date") LocalDate date) {
        return ResponseEntity.ok(lectureFacade.getLectureTimeListByDate(date));
    }

    // 특정 userId로 신청 완료된 특강 목록을 조회 - 특강 ID, 이름, 강연자 정보
    @GetMapping("/enrollment-list/{userId}")
    public ResponseEntity<List<EnrollmentListResponse>> getEnrolledLectures(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(lectureFacade.getEnrolledLectures(userId));
    }
}
