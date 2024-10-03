package com.hhplus.clean_architecture.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EnrollmentListResponse {

    private String lectureId;

    private String lectureName;

    private String teacherName;

    private LocalDateTime lectureTime;


}
