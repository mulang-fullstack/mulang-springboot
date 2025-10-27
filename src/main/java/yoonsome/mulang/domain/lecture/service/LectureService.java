package yoonsome.mulang.domain.lecture.service;

import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.lecture.entity.Lecture;

import java.io.IOException;
import java.util.List;

public interface LectureService {


    void createLectureWithFile(String title, String content, Course course, MultipartFile video)
            throws IOException;

    Lecture getLectureById(Long lectureId);

    List<Lecture> getLecturesByCourseId(Long courseId);
    // lecture 개수세기
    int countByCourse(Course course);

    //정렬
    List<Lecture> findByCourseOrdered(Course course);

    Lecture save(Lecture lecture);
}
