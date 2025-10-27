package yoonsome.mulang.api.admin.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.admin.content.dto.AdminCourseResponse;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminContentServiceImpl implements AdminContentService {

    private final CourseService courseService;

    @Override
    public Page<AdminCourseResponse> getCourseList(CourseListRequest request) {
        System.out.println(request.toString());
        Page<Course> coursePage = courseService.getCourseList(request);
        return coursePage.map(AdminCourseResponse::from);
    }
}