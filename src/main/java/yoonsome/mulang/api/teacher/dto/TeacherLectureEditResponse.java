package yoonsome.mulang.api.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherLectureEditResponse {

    private Long id;              // 강의 ID
    private String title;         // 강의 제목
    private String content;       // 강의 소개
    private String fileUrl;       // 업로드된 영상 파일 경로
    private String originalName;  // 원본 파일명
}
