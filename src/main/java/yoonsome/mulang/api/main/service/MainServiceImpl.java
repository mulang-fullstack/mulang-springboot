package yoonsome.mulang.api.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.course.dto.CourseListResponse;
import yoonsome.mulang.api.course.service.DisplayingCourseService;
import yoonsome.mulang.domain.course.dto.CourseListRequest;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.entity.StatusType;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.coursefavorite.service.CourseFavoriteService;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.language.service.LanguageService;

import java.util.*;

@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService {
    private final LanguageService languageService;
    private final DisplayingCourseService displayingCourseService;
    private final CourseService courseService;
    private final CourseFavoriteService courseFavoriteService;
    private final S3FileService s3FileService;

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
                    .thumbnail(s3FileService.getPublicUrl(course.getFile().getId()))
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
    @Override
    public Page<CourseListResponse> getCourseConversationPage(Long userId, CourseListRequest request){
        //status: 공개
        request.setStatus(StatusType.PUBLIC);
        //한 페이지에 가져올 강좌 수 4개
        request.setSize(4);

        // languageId → categoryId 매핑
        Map<Long, Long> conversationMap = Map.of(
                1L, 4L,  // 영어
                2L, 8L,  // 중국어
                3L, 12L  // 일본어
        );

        Long categoryId = conversationMap.getOrDefault(request.getLanguageId(), 4L);
        request.setCategoryId(categoryId);
        System.out.println("#mainService categoryId: " + categoryId+"request"+request);

        Page<Course> coursePage = courseService.getCourseList(request);
        //**CourseListResponse DTO 리스트 생성**//
        List<CourseListResponse> dtoList = new ArrayList<>();

        // 로그인 했을 시 찜 여부 정보 가져오기
        // 다회쿼리 방지 해당 user 찜한 set 모두 가져옴
        Set<Long> favoriteCourseIds = Collections.emptySet();
        if(userId != null){
            favoriteCourseIds = new HashSet<>(courseFavoriteService.getCourseIdsByUserId(userId));
        }
        for (Course course : coursePage) {
            // DTO 생성
            CourseListResponse dto = CourseListResponse.builder()
                    .id(course.getId())
                    .thumbnail(s3FileService.getPublicUrl(course.getFile().getId()))
                    .title(course.getTitle())
                    .subtitle(course.getSubtitle())
                    .teacherName(getTeacherName(course))
                    .averageRating(course.getAverageRating())
                    .reviewCount(course.getReviewCount())
                    .price(course.getPrice())
                    .createdAt(course.getCreatedAt())
                    .favorited(favoriteCourseIds.contains(course.getId()))
                    .build();
            dtoList.add(dto);
        }
        //** Pageable 생성 **//
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        //** PageImpl로 다시 Page 객체 생성 **//
        return new PageImpl<>(dtoList, pageable, coursePage.getTotalElements());
    }
    private Map<Long, Long> getConversationMap(){
        Map<Long, Long> map = new HashMap<>();
        map.put(1L, 4L);
        map.put(2L, 8L);
        map.put(3L, 12L);
        return map;
    }
    private List<CourseListResponse> makeDTOList(List<Course> courseList, Long userId) {
        List<CourseListResponse> dtoList = new ArrayList<>();
        for (Course course : courseList){
            CourseListResponse dto = CourseListResponse.builder()
                    .id(course.getId())
                    .thumbnail(s3FileService.getPublicUrl(course.getFile().getId()))
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
