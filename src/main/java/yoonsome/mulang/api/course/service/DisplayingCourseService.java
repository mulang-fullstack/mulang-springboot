package yoonsome.mulang.api.course.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.course.dto.CourseListRequest;
import yoonsome.mulang.api.course.dto.CourseListResponse;

public interface DisplayingCourseService {
    Page<CourseListResponse> getCourseList(CourseListRequest request, Pageable pageable);
    CourseDetailResponse getCourseDetail(long id);
}
