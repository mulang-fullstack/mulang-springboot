package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LectureUploadRequest {

    private Long id;
    private String title;
    private String content;
    private MultipartFile video;

}
