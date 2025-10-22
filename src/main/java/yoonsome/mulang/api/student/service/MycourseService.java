package yoonsome.mulang.api.student.service;

import yoonsome.mulang.domain.lecture.entity.Lecture;

import java.util.List;

public interface MycourseService {

    Lecture getLecture(Long lectureId);

    List<Lecture> getLectureListByCourseId(Long courseId);
}

