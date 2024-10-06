package com.hhplus.clean_architecture.interfaces.dto.request;

public record EnrollmentLectureRequest(
    String userId,
    Long lectureTimeId
) {

    public EnrollmentLectureRequest {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("유저 ID는 null이나 빈 값일 수 없습니다.");
        }

        if (lectureTimeId == null || lectureTimeId <= 0) {
            throw new IllegalArgumentException("Course time ID는 1이상이어야 합니다.");
        }
    }
}
