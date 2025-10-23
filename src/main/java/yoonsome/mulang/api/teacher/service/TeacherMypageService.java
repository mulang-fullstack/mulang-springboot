package yoonsome.mulang.api.teacher.service;

import yoonsome.mulang.api.teacher.dto.CourseUploadRequest;
import yoonsome.mulang.api.teacher.dto.TeacherProfileUpdateRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;
import java.io.IOException;
import java.util.List;

public interface TeacherMypageService {

    // 교사 본인의 강좌 목록 조회
    List<Course> getTeacherCourses(Long userId);

    // 교사 프로필 조회
    Teacher getTeacherProfile(Long userId);

    // 교사 프로필 수정
    void updateTeacherProfile(Long userId, TeacherProfileUpdateRequest request) throws IOException;

    // 강좌 생성
    void createCourse(Long userId, CourseUploadRequest request) throws IOException;
}
