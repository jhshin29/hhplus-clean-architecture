package com.hhplus.clean_architecture.dto.response;

import com.hhplus.clean_architecture.entity.LectureTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LectureTimeListResponse {

    private String lectureId;

    private int capacity;

    private LocalDateTime lectureTime;

    private boolean isClosed;

//    private int currentRegistrations;
}
