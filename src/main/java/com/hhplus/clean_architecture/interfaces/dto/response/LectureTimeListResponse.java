package com.hhplus.clean_architecture.interfaces.dto.response;

import java.time.LocalDateTime;

public record LectureTimeListResponse(
        String lectureId,
        int capacity,
        LocalDateTime lectureTime
) {

}
