package yoonsome.mulang.lecture.service;

import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;

import java.io.IOException;

public interface LectureService {

    void createLectureWithFile(String title, Course course, MultipartFile video) throws IOException;
}
