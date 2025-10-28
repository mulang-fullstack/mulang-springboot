package yoonsome.mulang.api.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.course.service.CourseService;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.service.LectureService;

import java.util.List;

/**
 * 공용 VOD 뷰 컨트롤러 (어노테이션 적용 전 임시 버전)
 * ------------------------------------------------------------
 * 역할: 학생 / 교사 / 어드민이 모두 접근 가능한 강의 재생 페이지를 제공.
 *
 * <접근 URL>
 * - 공통: /course/{courseId}/vod
 *
 * <현재 단계>
 * - 추후 팀장이 제작할  어노테이션으로 접근 제어를 적용할 예정.
 * - 현재는 권한 체크를 HttpServletRequest로 임시 수행.
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

        model.addAttribute("course", course);
        model.addAttribute("lectureList", lectureList);

        // [2] 재생할 강의 선택 (lectureId 지정 시 해당 강의, 없으면 첫 강의)
        Lecture lecture = null;
        if (lectureId != null) {
            lecture = lectureService.getLectureById(lectureId);
        } else if (!lectureList.isEmpty()) {
            lecture = lectureList.get(0);
        }
        model.addAttribute("lecture", lecture);

        // [3] (임시) 역할 로그 확인용 — 나중에 제거
        if (request.isUserInRole("ROLE_ADMIN")) {
            System.out.println("[VOD 접근] ADMIN");
        } else if (request.isUserInRole("ROLE_TEACHER")) {
            System.out.println("[VOD 접근] TEACHER");
        } else if (request.isUserInRole("ROLE_STUDENT")) {
            System.out.println("[VOD 접근] STUDENT");
        }
        // [4] JSP 반환
        return "common/player";
    }
}
