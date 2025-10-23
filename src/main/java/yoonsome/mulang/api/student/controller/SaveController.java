package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;
import yoonsome.mulang.domain.coursefavorite.repository.CourseFavoriteRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class SaveController {

    private final CourseFavoriteRepository courseFavoriteRepository;

    @GetMapping("/save")
    public String getFavorites(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model, @RequestParam(defaultValue = "0") int page) {

        // CustomUserDetails에서 바로 User ID 가져오기
        Long userId = userDetails.getUser().getId();

        // User ID로 찜 목록 조회
        /*List<CourseFavorite> favorites =
                courseFavoriteRepository.findByStudentIdWithCourse(userId);*/
        //페이지네이션
        Pageable pageable =  PageRequest.of(page, 5);
        Page<CourseFavorite> favorites =
                courseFavoriteRepository.findByStudentIdWithCourse(userId, pageable);


        model.addAttribute("favorites", favorites.getContent());  // .getContent() 추가!
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", favorites.getTotalPages());

        return "student/like/save";
    }

}
