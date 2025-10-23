package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yoonsome.mulang.domain.coursefavorite.repository.CourseFavoriteRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;


@RequiredArgsConstructor
@RestController
@RequestMapping("delete")
public class SaveCancelController {

    private final CourseFavoriteRepository courseFavoriteRepository;


    @DeleteMapping("/{courseId}")// 삭제하는 http 매핑
    public ResponseEntity<Void> removeFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId) {

        Long userId = userDetails.getUser().getId();

        // 찜 삭제
        courseFavoriteRepository.deleteByStudentIdAndCourseId(userId, courseId);

        return ResponseEntity.ok().build();
    }
}
