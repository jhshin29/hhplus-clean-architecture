package com.hhplus.clean_architecture.facade;

import com.hhplus.clean_architecture.dto.request.EnrollmentLectureRequest;
import com.hhplus.clean_architecture.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnrollmentFacade {

    private final EnrollmentService enrollmentService;

    @Transactional(rollbackFor = Exception.class)
    public boolean enroll(EnrollmentLectureRequest request) {
        enrollmentService.enroll(request.userId(), request.LectureTimeId());
        return true;
    }


}
