package yoonsome.mulang.api.student.service;


import yoonsome.mulang.api.student.dto.MycourseResponse;


import java.util.List;

public interface MyCourseInfoService {

    List<MycourseResponse> findByUserId(Long userId);
    List<MycourseResponse> findByUserIdWithCourse(Long userId);
    List<MycourseResponse> findByUserIdAndLanguage(Long userId, Long languageId);//과목별 정렬
    List<MycourseResponse> findByUserIdAndKeyword(Long userId, String keyword);  // 키워드 검색
    List<MycourseResponse> findByUserIdAndLanguageAndKeyword(Long userId, Long languageId, String keyword); // 키워드 검색 + 과목별


}
