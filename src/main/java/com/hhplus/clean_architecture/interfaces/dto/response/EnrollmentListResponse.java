package com.hhplus.clean_architecture.interfaces.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public record EnrollmentListResponse(
        String lectureId,
        String lectureName,
        String teacherName,
        LocalDateTime lectureTime
) {

}
