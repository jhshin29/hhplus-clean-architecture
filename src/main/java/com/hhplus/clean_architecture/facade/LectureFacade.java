package com.hhplus.clean_architecture.facade;

import com.hhplus.clean_architecture.dto.request.EnrollmentLectureRequest;
import com.hhplus.clean_architecture.dto.response.EnrollmentListResponse;
import com.hhplus.clean_architecture.dto.response.LectureTimeListResponse;
import com.hhplus.clean_architecture.service.EnrollmentService;
import com.hhplus.clean_architecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;
    private final EnrollmentService enrollmentService;

    public List<LectureTimeListResponse> getLectureTimeListByDate(LocalDate date) {
        return lectureService.getLectureTimeListByDate(date);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean enroll(EnrollmentLectureRequest request) {
        enrollmentService.enroll(request.userId(), request.lectureTimeId());
        return true;
    }

    public List<EnrollmentListResponse> getEnrolledLectures(String userId) {
        return enrollmentService.getEnrolledLectures(userId);
    }
}
