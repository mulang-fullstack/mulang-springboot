package yoonsome.mulang.api.teacher.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.teacher.dto.CourseUpdateRequest;
import yoonsome.mulang.api.teacher.dto.CourseUploadRequest;
import yoonsome.mulang.api.teacher.dto.TeacherCourseResponse;

import java.io.IOException;

public interface TeacherCourseService {

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
    // 에디터 본문 이미지만 url 교체
    void updateEditorImage(Long userId, Long courseId, String oldImageUrl, MultipartFile newImage) throws IOException;
}
