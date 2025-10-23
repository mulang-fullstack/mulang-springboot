package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.teacher.entity.Teacher;


@Data
public class TeacherProfileUpdateRequest {
    private String introduction;
    private String career;
    private MultipartFile photo;
    private String nickname;
    private String email;

    public void applyTo(Teacher teacher) {
        teacher.setIntroduction(this.introduction);
        teacher.setCareer(this.career);
    }
}
