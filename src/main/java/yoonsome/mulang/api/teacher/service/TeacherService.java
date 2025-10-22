package yoonsome.mulang.api.teacher.service;

import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;
import java.io.IOException;
import java.util.List;

public interface TeacherService {

    //userId 가 소속된 교사의 모든 강좌 목록을 조회
    List<Course> getTeacherCourses(Long userId);

    // userId 에 해당하는 교사 프로필 정보를 조회
    Teacher getTeacherProfile(Long userId);

    // 교사 프로필 정보 수정
    void updateTeacherProfile(Long userId, Teacher updateData, MultipartFile photo) throws IOException;

    // 새로운 교사 엔티티를 생성하고 사용자와 연결
    // 회원가입 완료 후 교사 행을 자동으로 생성할 때 사용
    Teacher createTeacher(User user);
}
