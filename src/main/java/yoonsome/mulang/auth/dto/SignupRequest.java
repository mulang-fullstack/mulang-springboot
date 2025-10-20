package yoonsome.mulang.auth.dto;

import lombok.Data;

@Data
public class SignupRequest {
        private String name;
        private String nickname;
        private String email;
        private String password;
        private String passwordConfirm;
        private String accountType;
}
