package com.hhplus.clean_architecture.facade;

import com.hhplus.clean_architecture.dto.response.LectureTimeListResponse;
import com.hhplus.clean_architecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;

    public List<LectureTimeListResponse> searchLectureTimeListByDate(LocalDate date) {
        return lectureService.searchLectureTimeListByDate(date);
    }
}
