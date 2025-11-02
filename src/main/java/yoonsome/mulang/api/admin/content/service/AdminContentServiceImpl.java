package yoonsome.mulang.api.admin.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.admin.content.dto.AdminCourseResponse;
import yoonsome.mulang.api.admin.content.dto.AdminStatusUpdateRequest;
import yoonsome.mulang.api.admin.user.dto.UserUpdateRequest;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.user.entity.User;

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

    @Override
    @Transactional
    public void updateCourseInfo(Long userId, AdminStatusUpdateRequest request) {
        Course course = courseService.getCourse(userId);
        if (request.getStatus() != null) course.setStatus(request.getStatus());
        if (request.getRejectionReason() != null) course.setRejectionReason(request.getRejectionReason());
    }
}