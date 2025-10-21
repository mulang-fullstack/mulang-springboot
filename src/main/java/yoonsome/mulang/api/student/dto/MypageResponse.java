package yoonsome.mulang.api.student.dto;

import lombok.Data;

@Data
public class MypageResponse {
    private Long id;
    private String email;
    private String nickname;
    private String username;
}
