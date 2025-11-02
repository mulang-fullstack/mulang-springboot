package yoonsome.mulang.domain.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yoonsome.mulang.domain.teacher.entity.Teacher;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {

    private Long teacherId;
    private String introduction;
    private String career;
    private Long userId;
    private String userEmail;
    private String userName;

    public static TeacherResponse from(Teacher teacher) {
        return TeacherResponse.builder()
                .teacherId(teacher.getId())
                .introduction(teacher.getIntroduction())
                .career(teacher.getCareer())
                .userId(teacher.getUser().getId())
                .userEmail(teacher.getUser().getEmail())
                .userName(teacher.getUser().getNickname())
                .build();
    }
}
