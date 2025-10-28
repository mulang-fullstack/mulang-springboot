package yoonsome.mulang.api.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.course.service.DisplayingCourseService;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.service.LanguageService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {
    private final LanguageService languageService;
    private final DisplayingCourseService displayingCourseService;
    private final CourseService courseService;

    @Override
    public List<Language> getLanguageList() {
        return languageService.getAllLanguages();
    }

    @Override
    public List<CourseListResponse> getCourseRankingPage(long languageId) {
        List<Course> courseList = courseService.getCourseRankingList(languageId);
        List<CourseListResponse> dtoList = new ArrayList<>();
        for (Course course : courseList){
            CourseListResponse dto = CourseListResponse.builder()
                    .id(course.getId())
                    .thumbnail(course.getThumbnail())
                    .title(course.getTitle())
                    .subtitle(course.getSubtitle())
                    .teacherName(getTeacherName(course))
                    .averageRating(course.getAverageRating())
                    .reviewCount(course.getReviewCount())
                    .price(course.getPrice())
                    .createdAt(course.getCreatedAt())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }
    //해당 강좌의 강사 이름
    private String getTeacherName(Course course) {
        String teacherName = course.getTeacher().getUser().getNickname();
        return teacherName;
    }
}
