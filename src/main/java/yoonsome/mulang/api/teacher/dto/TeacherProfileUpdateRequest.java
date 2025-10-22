package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class TeacherProfileUpdateRequest {
    private String introduction;
    private String carreer;
    private MultipartFile photo;
}
