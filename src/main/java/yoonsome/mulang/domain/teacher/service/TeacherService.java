package yoonsome.mulang.domain.teacher.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;

import java.util.List;

public interface TeacherService {

    // 교사 생성
    Teacher createTeacher(User user);

    // 유저 ID로 교사 조회
    Teacher getTeacherByUserId(Long userId);

    // 교사 ID로 단건 조회
    Teacher getTeacherById(Long teacherId);

    // 전체 교사 조회
    List<Teacher> getAllTeachers();

    // 페이징 전체 교사 조회
    Page<Teacher> getAllTeachers(Pageable pageable);

    // 키워드로 교사 검색
    List<Teacher> searchTeachers(String keyword);
}
