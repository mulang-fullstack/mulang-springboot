package yoonsome.mulang.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.auth.dto.LoginRequest;
import yoonsome.mulang.auth.dto.SignupRequest;
import yoonsome.mulang.user.entity.User;
import yoonsome.mulang.user.service.UserService;
import yoonsome.mulang.util.BCryptEncoder;

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
    public boolean signup(SignupRequest request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // User 엔티티 생성
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setUsername(request.getUsername());
        newUser.setNickname(request.getNickname());
        newUser.setRole(
                request.getAccountType().equalsIgnoreCase("T")
                        ? User.Role.TEACHER
                        : User.Role.STUDENT
        );

        // DB 저장 & 저장된 정보 반환
        User user = userService.saveUser(newUser);
        // 저장 성공 여부 반환
        return user != null && user.getId() != null;
    }

    @Override
    public boolean isEmailExists(String email) {
        return userService.existsByEmail(email);
    }
}
