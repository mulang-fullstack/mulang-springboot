package yoonsome.mulang.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.auth.dto.LoginRequest;
import yoonsome.mulang.auth.dto.SignupRequest;
import yoonsome.mulang.user.entity.User;
import yoonsome.mulang.user.service.UserService;
import yoonsome.mulang.user.util.BCryptEncoder;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final BCryptEncoder passwordEncoder;

    @Override
    public User login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    @Override
    public void signup(SignupRequest request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // User 엔티티 생성
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setUsername(request.getName());
        user.setNickname(request.getNickname());
        user.setRole(
                request.getAccountType().equalsIgnoreCase("S")
                        ? User.Role.STUDENT
                        : User.Role.TEACHER
        );

        // DB 저장 (UserService 통해서)
        userService.saveUser(user);
    }
}
