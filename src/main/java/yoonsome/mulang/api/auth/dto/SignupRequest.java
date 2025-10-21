package yoonsome.mulang.api.auth.dto;

import lombok.Data;

@Data
public class SignupRequest {
        private String username;
        private String nickname;
        private String email;
        private String password;
        private String passwordConfirm;
        private String accountType;
}
