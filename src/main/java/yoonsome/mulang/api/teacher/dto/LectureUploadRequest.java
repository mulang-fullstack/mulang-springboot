package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LectureUploadRequest {
    private String title;
    private MultipartFile video;
}
