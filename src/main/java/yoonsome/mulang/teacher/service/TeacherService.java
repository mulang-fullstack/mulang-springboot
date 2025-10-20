package yoonsome.mulang.teacher.service;

import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.teacher.entity.Teacher;

import java.io.IOException;
import java.util.List;

public interface TeacherService {

    List<Course> getTeacherCourses(Long userId);
    Teacher getTeacherProfile(Long userId);
    void updateTeacherProfile(Long userId, Teacher updateData, MultipartFile photo) throws IOException;
}

