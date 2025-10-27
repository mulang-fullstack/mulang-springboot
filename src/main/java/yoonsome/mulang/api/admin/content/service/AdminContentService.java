package yoonsome.mulang.api.admin.content.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.content.dto.AdminCourseResponse;
import yoonsome.mulang.domain.course.dto.CourseListRequest;

public interface AdminContentService {
    /**
     * 관리자 강좌 목록 조회 (페이징)
     * @param request 검색 및 필터 조건
     * @return 강좌 목록 페이지
     */
    Page<AdminCourseResponse> getCourseList(CourseListRequest request);
}