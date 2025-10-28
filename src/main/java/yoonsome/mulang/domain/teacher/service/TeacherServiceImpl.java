package yoonsome.mulang.domain.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.repository.TeacherRepository;
import yoonsome.mulang.domain.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    // 교사 생성
    @Override
    public Teacher createTeacher(User user) {
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }

    // 유저 ID로 교사 조회
    @Override
    public Teacher getTeacherByUserId(Long userId) {
        return teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("교사 정보를 찾을 수 없습니다."));
    }

    // 교사 ID로 단건 조회
    @Override
    public Teacher getTeacherById(Long teacherId) {
        return teacherRepository.findByTeacherId(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 교사를 찾을 수 없습니다."));
    }

}
