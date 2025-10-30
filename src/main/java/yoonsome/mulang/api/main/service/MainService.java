package yoonsome.mulang.api.main.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.language.entity.Language;

import java.util.List;
import java.util.Map;

public interface MainService {
    //실시간 랭킹 언어 카테고리
    List<Language> getLanguageList();
    //주간BEST 강의 리스트
    List<CourseListResponse> getBestCourseList();
    //실시간 랭킹 강의 리스트
    List<CourseListResponse> getCourseRanking(Long userId, long languageId);
    //신규 클래스 리스트
    List<CourseListResponse> getNewCourseList(Long userId);
    //일상회화 리스트
    Page<CourseListResponse> getCourseConversationPage(Long userId, CourseListRequest request);
}
