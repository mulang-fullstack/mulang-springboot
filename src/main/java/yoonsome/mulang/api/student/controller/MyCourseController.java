package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import yoonsome.mulang.api.student.dto.MycourseResponse;
import yoonsome.mulang.api.student.service.MyCourseInfoService;
import yoonsome.mulang.api.student.service.MycourseService;
import yoonsome.mulang.domain.enrollment.repository.EnrollmentRepository;
import yoonsome.mulang.domain.lecture.entity.Lecture;
import yoonsome.mulang.domain.lecture.repository.LectureRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class MyCourseController {

    private final MyCourseInfoService mycourseInfoService;

    @GetMapping("/course")
    public String course(@AuthenticationPrincipal CustomUserDetails userDetails,Model model) {

        Long userid = userDetails.getUser().getId();
        List<MycourseResponse> mycourseDTO =  mycourseInfoService.findByUserId(userid);

        model.addAttribute("mycourseDTO", mycourseDTO);


        return "student/mycourse/course";
    }





     /*임시 더미더미
    @GetMapping("/player")
    public String player(Model model) {
        Long testCourseId = 20L;
        Long testLectureId = 16L;

        Lecture lecture = mycourseService.getLecture(testLectureId);
        List<Lecture> lectureList = mycourseService.getLectureListByCourseId(testCourseId);

        model.addAttribute("lecture", lecture);
        model.addAttribute("lectureList", lectureList);
        return "student/mycourse/player";
    }

    @GetMapping("/{courseId}/lecture/{lectureId}")
    public String playLecture(@PathVariable Long courseId,
                              @PathVariable Long lectureId,
                              @AuthenticationPrincipal CustomUserDetails userDetails,
                              Model model) {

        Lecture lecture = mycourseService.getLecture(lectureId);
        List<Lecture> lectureList = mycourseService.getLectureListByCourseId(courseId);

        model.addAttribute("lecture", lecture);
        model.addAttribute("lectureList", lectureList);
        model.addAttribute("user", userDetails);

        return "student/mycourse/player";
    }

      */
}
