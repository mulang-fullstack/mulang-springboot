package yoonsome.mulang.api.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.auth.dto.SignupRequest;
import yoonsome.mulang.domain.email.service.EmailCodeService;
import yoonsome.mulang.domain.teacher.service.TeacherService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.repo.FileRepository;
import yoonsome.mulang.infra.file.service.FileService;
import yoonsome.mulang.infra.mail.MailService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final EmailCodeService emailCodeService;
    private final TeacherService teacherService;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public boolean signup(SignupRequest request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        // 기본 프로필 이미지 조회 (ID: 1)
        File defaultFile = fileRepository.findById(1L)
                .orElse(null);  // 없으면 null (나중에 처리)
        // User 엔티티 생성
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setUsername(request.getUsername());
        newUser.setNickname(request.getNickname());
        newUser.setFile(defaultFile);
        newUser.setRole(
                request.getAccountType().equalsIgnoreCase("T")
                        ? User.Role.TEACHER
                        : User.Role.STUDENT
        );
        // DB 저장 & 저장된 정보 반환
        User savedUser = userService.saveUser(newUser);
        // 3. 강사 계정인 경우 teacher 테이블에 튜플 생성
        if (savedUser.getRole() == User.Role.TEACHER) {
            teacherService.createTeacher(savedUser);
        }
        // 저장 성공 여부 반환
        return savedUser.getId() != null;
    }

    @Override
    @Transactional
    public void sendSignupVerification(String email) {
        // 6자리 인증코드 생성
        String code = generateCode();
        // 인증 메일 전송 (메일 제목·본문은 MailService에서 처리)
        mailService.sendVerificationEmail(email, code);
        // 이후 code를 DB에 저장
        emailCodeService.saveCode(email, code);
    }

    private String generateCode() {
        // 100000 ~ 999999 범위의 6자리 난수 생성
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}
