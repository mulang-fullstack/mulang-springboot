package yoonsome.mulang.domain.teacher.service;

import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.user.entity.User;


public interface TeacherService {

    // 교사 생성
    Teacher createTeacher(User user);

    // 유저 ID로 교사 조회
    Teacher getTeacherByUserId(Long userId);

    // 교사 ID로 단건 조회
    Teacher getTeacherById(Long teacherId);

}
