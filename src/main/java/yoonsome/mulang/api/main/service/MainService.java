package yoonsome.mulang.api.main.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.domain.language.entity.Language;

import java.util.List;

public interface MainService {
    //실시간 랭킹 언어 카테고리
    List<Language> getLanguageList();
    //실시간 랭킹 강의 리스트
    List<CourseListResponse> getCourseRankingPage(long languageId);
}
