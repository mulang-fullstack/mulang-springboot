package yoonsome.mulang.api.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherProfileResponse {
    private String name;
    private String nickname;
    private String email;
    private String introduction;
    private String career;
    private String photoUrl;

}
