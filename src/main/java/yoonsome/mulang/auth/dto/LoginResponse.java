package yoonsome.mulang.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String email;
    private String password;
}
