package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class CourseUploadRequest {

    private String title;           // 클래스 이름
    private String subtitle;        // 부제목
    private String content;         // 강의 소개 (CKEditor 내용)
    private Integer price;          // 수강비용

    /** 카테고리/언어 선택 */
    private Long categoryId;        // 카테고리 ID
    private Long languageId;        // 언어 ID

    /** 상태 정보 */
    //private CourseStatus status;    // 공개 여부 (PUBLIC, PRIVATE, PENDING)
    private Integer lectureCount;   // 강의 개수

    /** 파일 */
    private MultipartFile thumbnailFile;    // 썸네일 이미지

    /** 하위 챕터(강의) 업로드 요청 리스트 */
    private List<LectureUploadRequest> lectures;

    //임시임시 확장할때
    //private Boolean status = true;
    //private Integer lectureCount = 1;
}