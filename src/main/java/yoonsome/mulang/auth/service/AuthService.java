package yoonsome.mulang.auth.service;

import yoonsome.mulang.auth.dto.LoginRequest;
import yoonsome.mulang.auth.dto.SignupRequest;
import yoonsome.mulang.user.entity.User;

public interface AuthService {
    User login(LoginRequest request);
    void signup(SignupRequest request);
}
