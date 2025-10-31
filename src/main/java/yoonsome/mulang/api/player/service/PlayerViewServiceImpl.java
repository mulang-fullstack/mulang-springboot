package yoonsome.mulang.api.player.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.player.dto.VodCourseResponse;
import yoonsome.mulang.api.player.dto.VodLectureResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.progress.service.ProgressShowService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.file.service.S3FileService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlayerViewServiceImpl implements PlayerViewService {

    private final CourseService courseService;
    private final LectureService lectureService;
    private final S3FileService s3FileService;
    private final ProgressShowService progressShowService;

    @Override
    public Map<String, Object> getPlayerView(Long courseId, Long lectureId, User user) {

        Map<String, Object> result = new HashMap<>();

        // [1] 강좌 조회 및 DTO 빌드
        Course courseEntity = courseService.getCourse(courseId);
        VodCourseResponse course = VodCourseResponse.builder()
                .courseId(courseEntity.getId())
                .title(courseEntity.getTitle())
                .subtitle(courseEntity.getSubtitle())
                .language(courseEntity.getLanguage() != null ? courseEntity.getLanguage().getName() : null)
                .category(courseEntity.getCategory() != null ? courseEntity.getCategory().getName() : null)
                .price(courseEntity.getPrice())
                .statusText(courseEntity.getStatus() != null ? courseEntity.getStatus().getBadgeText() : null)
                .build();

        // [2] 강의 목록 조회
        List<Lecture> lectureList = lectureService.getLecturesByCourseId(courseId);

        // [3] 현재 재생 강의 선택
        Lecture currentLectureEntity = null;
        if (lectureId != null) {
            currentLectureEntity = lectureService.getLectureById(lectureId);
        } else if (!lectureList.isEmpty()) {
            currentLectureEntity = lectureList.get(0);
        }

        // [4] presigned URL 생성
        String presignedUrl = null;
        if (currentLectureEntity != null && currentLectureEntity.getFile() != null) {
            presignedUrl = s3FileService.getPresignedUrl(currentLectureEntity.getFile().getId(), 5);
        }

        // [5] 학생 진도 처리
        if (user != null && user.getRole() == User.Role.STUDENT && currentLectureEntity != null) {
            progressShowService.LectureCompleted(user.getId(), currentLectureEntity.getId());
        }

        // [6] 강의 목록 DTO
        List<VodLectureResponse> lectureResponses = lectureList.stream()
                .map(lec -> VodLectureResponse.builder()
                        .id(lec.getId())
                        .title(lec.getTitle())
                        .fileUrl(null)
                        .build())
                .toList();

        // [7] 현재 강의 DTO
        VodLectureResponse currentLecture = null;
        if (currentLectureEntity != null) {
            currentLecture = VodLectureResponse.builder()
                    .id(currentLectureEntity.getId())
                    .title(currentLectureEntity.getTitle())
                    .fileUrl(presignedUrl)
                    .build();
        }

        // [8] 반환 구성
        result.put("course", course);
        result.put("lectureList", lectureResponses);
        result.put("lecture", currentLecture);

        return result;
    }
}
