package yoonsome.mulang.api.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import yoonsome.mulang.api.common.dto.VodLectureResponse;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;

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

    @GetMapping("/course/{courseId}/vod")
    public String showVodPage(@PathVariable Long courseId,
                              @RequestParam(required = false) Long lectureId,
                              HttpServletRequest request,
                              Model model) {

        // [1] 강좌 및 강의 목록 조회
        Course course = courseService.getCourse(courseId);
        List<Lecture> lectureList = lectureService.getLecturesByCourseId(courseId);

        // [2] DTO 변환 (엔티티 직접 노출 금지)
        List<VodLectureResponse> vodLectureList = lectureList.stream()
                .map(lec -> VodLectureResponse.builder()
                        .id(lec.getId())
                        .title(lec.getTitle())
                        .fileUrl(lec.getFile() != null ? lec.getFile().getUrl() : null)
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
        if (currentLecture == null && !vodLectureList.isEmpty()) {
            currentLecture = vodLectureList.get(0);
        }

        // [4] 모델에 DTO만 전달
        model.addAttribute("course", course);
        model.addAttribute("lectureList", vodLectureList);
        model.addAttribute("lecture", currentLecture);

        return "common/player";
    }
}
