package yoonsome.mulang.api.student.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yoonsome.mulang.api.student.service.SaveService;
import yoonsome.mulang.infra.security.CustomUserDetails;


@RequiredArgsConstructor
@RestController
@RequestMapping("delete")
public class SaveCancelController {

    private final SaveService saveService;


    @DeleteMapping("/{courseId}")// 삭제하는 http 매핑
    public ResponseEntity<Void> removeFavorite(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId) {

        Long userId = userDetails.getUser().getId();

        // 찜 삭제
        saveService.deleteByStudentIdAndCourseId(userId, courseId);

        return ResponseEntity.ok().build();
    }
}
