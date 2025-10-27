package yoonsome.mulang.api.teacher.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.course.dto.CourseDetailResponse;
import yoonsome.mulang.api.teacher.dto.*;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;

import java.io.IOException;
import java.util.List;

public interface TeacherMypageService {

    // 교사 프로필 조회
    TeacherProfileResponse getTeacherProfileResponse(Long userId);

    // 교사 프로필 수정
    void updateTeacherProfile(Long userId, TeacherProfileUpdateRequest request) throws IOException;

    // 강좌 생성
    void createCourse(Long userId, CourseUploadRequest request) throws IOException;

    // 수정할때 단건조회
    TeacherCourseResponse getCourseDetail(Long userId, Long courseId);

    //강좌 업데이트 수정
    void updateCourse(Long userId, Long courseId, CourseUpdateRequest request) throws IOException;

    // 강좌 삭제 상태변경
    void deleteCourse(Long userId, Long courseId);

    // 교사 본인의 강좌 목록 조회
    Page<TeacherCourseResponse> getTeacherCoursePage(Long userId, Pageable pageable);
}
