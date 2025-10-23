package yoonsome.mulang.api.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;

@Transactional
@RequiredArgsConstructor
@Service
public class DisplayingCourseServiceImpl implements DisplayingCourseService {
    @Override
    public Page<CourseListResponse> getCourseList(CourseListRequest request, Pageable pageable) {
        return null;
    }

    @Override
    public CourseDetailResponse getCourseDetail(long id) {
        return null;
    }
}
