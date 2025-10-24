package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.course.entity.StatusType;


@Data
public class CourseUpdateRequest {

    private Long courseId;           // 수정할 강좌 ID
    private String title;            // 클래스 이름
    private String subtitle;         // 부제목
    private String content;          // 강의 소개
    private Integer price;           // 수강비용
    private StatusType status;       // 공개 여부 (PUBLIC, PRIVATE, PENDING)
    private Integer lectureCount;    // 강의 개수
    private Long categoryId;         // 카테고리 ID
    private Long languageId;         // 언어 ID
    private MultipartFile thumbnailFile;  // 썸네일 이미지
}
