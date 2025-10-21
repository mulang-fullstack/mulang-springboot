package yoonsome.mulang.api.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String email;
    private String password;
}
