package yoonsome.mulang.domain.lecture.service;


import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import java.util.List;

public interface LectureService {


        /** 강의 ID로 단건 조회 */
        Lecture getLectureById(Long lectureId);

        /** 코스 ID로 강의 목록 조회 */
        List<Lecture> getLecturesByCourseId(Long courseId);

        /** 특정 코스에 속한 강의 개수 반환 */
        int countByCourse(Course course);

        /** 순서 orderIndex 오름차순 정렬 조회 */
        List<Lecture> findByCourseOrdered(Course course);

        /** 강의 저장 신규 또는 수정 */
        Lecture save(Lecture lecture);

        /** 강의 ID로 삭제 */
        void delete(Long lectureId);

}
