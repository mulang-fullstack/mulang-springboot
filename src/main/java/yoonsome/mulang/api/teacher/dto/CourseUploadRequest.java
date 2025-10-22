package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class CourseUploadRequest {
    private String title;
    private String subtitle;
    private String content;
    private Integer price;
    private MultipartFile thumbnail;
    private List<LectureUploadRequest> lectures;
}