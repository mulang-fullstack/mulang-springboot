package yoonsome.mulang.api.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.course.service.DisplayingCourseService;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.coursefavorite.service.CourseFavoriteService;
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
    private final CourseFavoriteService courseFavoriteService;

    @Override
    public List<Language> getLanguageList() {
        return languageService.getAllLanguages();
    }

    @Override
    public List<CourseListResponse> getBestCourseList() {
        List<Course> courseList = courseService.getCourseRankingList(0);
        List<CourseListResponse> dtoList = new ArrayList<>();
        for (Course course : courseList){
            CourseListResponse dto = CourseListResponse.builder()
                    .id(course.getId())
                    .thumbnail(course.getThumbnail())
                    .title(course.getTitle())
                    .averageRating(course.getAverageRating())
                    .reviewCount(course.getReviewCount())
                    .price(course.getPrice())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }
    @Override
    public List<CourseListResponse> getCourseRanking(Long userId, long languageId) {
        List<Course> courseList = courseService.getCourseRankingList(languageId);
        return makeDTOList(courseList, userId);
    }
    @Override
    public List<CourseListResponse> getNewCourseList(Long userId) {
        List<Course> courseList = courseService.getNewCourseList();
        return makeDTOList(courseList, userId);
    }
    private List<CourseListResponse> makeDTOList(List<Course> courseList, Long userId) {
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
                    .favorited(existsCourseFavorite(userId, course.getId()))
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
    public boolean existsCourseFavorite(Long userId, long courseId) {
        return courseFavoriteService.existsCourseFavorite(userId, courseId);
    }
}
