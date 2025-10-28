package yoonsome.mulang.api.student.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yoonsome.mulang.api.student.dto.SaveResponse;


import java.util.List;

public interface SaveService {
    Page<SaveResponse> findByStudentIdWithCoursePage(Long studentId, Pageable pageable);//전체과목
    Page<SaveResponse> findByStudentIdAndLanguage(Long studentId, Long languageId, Pageable pageable);//과목별 정렬

    Page<SaveResponse> findByStudentIdAndKeyword(Long studentId, String keyword, Pageable pageable);
    Page<SaveResponse> findByStudentIdAndLanguageAndKeyword(Long  studentId, Long languageId, String keyword, Pageable pageable);

    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);//삭제
}
