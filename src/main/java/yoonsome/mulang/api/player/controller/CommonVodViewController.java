package yoonsome.mulang.api.player.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import yoonsome.mulang.api.player.dto.VodLectureResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;
import yoonsome.mulang.domain.progress.service.ProgressShowService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.infra.file.service.S3FileService;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ------------------------------------------------------------
 * 역할: 학생 / 교사 / 어드민이 모두 접근 가능한 강의 재생 페이지를 제공.
 *
 * <접근 URL>
 * - /course/{courseId}/vod
 *
 * <주요 기능>
 *  강좌 및 강의 목록 조회
 *  VodLectureResponse DTO 로 엔티티 매핑 후 JSP 전달
 *  공용 JSP (/WEB-INF/views/common/player.jsp) 렌더링
 */
@Controller
@RequiredArgsConstructor
public class CommonVodViewController {

    private final CourseService courseService;
    private final LectureService lectureService;
    private final ProgressShowService progressShowService;
    private final S3FileService s3FileService;

    @GetMapping("/player/{courseId}")
    public String showVodPage(@PathVariable Long courseId,
                              @RequestParam(required = false) Long lectureId,
                              @AuthenticationPrincipal CustomUserDetails userDetails,
                              Model model) {

        // [1] 강좌 및 강의 목록 조회
        Course course = courseService.getCourse(courseId);
        List<Lecture> lectureList = lectureService.getLecturesByCourseId(courseId);

        // [2] DTO 변환
        List<VodLectureResponse> vodLectureList = lectureList.stream()
                .map(lec -> VodLectureResponse.builder()
                        .id(lec.getId())
                        .title(lec.getTitle())
                        .fileUrl(
                            lec.getFile() != null
                                    ? s3FileService.getPresignedUrl(lec.getFile().getId(), 2)
                                    : null
                        )
                        .build())
                .collect(Collectors.toList());

        // [3] 현재 재생할 강의 선택
        VodLectureResponse currentLecture = null;
        if (lectureId != null) {
            currentLecture = vodLectureList.stream()
                    .filter(l -> l.getId().equals(lectureId))
                    .findFirst()
                    .orElse(null);
        }

        //  currentLecture가 null이면 첫 번째 강의 선택 (이 부분을 먼저!)
        if (currentLecture == null && !vodLectureList.isEmpty()) {
            currentLecture = vodLectureList.get(0);
        }

        //  학생이고 currentLecture가 null이 아닐 때만 진도 처리
        User user = userDetails.getUser();
        if (user.getRole() == User.Role.STUDENT && currentLecture != null) {
            progressShowService.LectureCompleted(user.getId(), currentLecture.getId());
        }

        // [4] 모델에 데이터 전달
        model.addAttribute("course", course);
        model.addAttribute("lectureList", vodLectureList);
        model.addAttribute("lecture", currentLecture);

        return "common/player";
    }
}

