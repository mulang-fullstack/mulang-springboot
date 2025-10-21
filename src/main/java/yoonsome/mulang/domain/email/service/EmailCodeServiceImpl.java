package yoonsome.mulang.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.domain.email.entity.EmailCode;
import yoonsome.mulang.domain.email.repository.EmailCodeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailCodeServiceImpl implements EmailCodeService {

    private final EmailCodeRepository repository;

    /**
     * 인증 코드 저장 (기존 이메일 있으면 갱신)
     */
    @Override
    @Transactional
    public void saveCode(String email, String code) {
        Optional<EmailCode> existing = repository.findByEmail(email);

        if (existing.isPresent()) {
            // 기존 레코드 → 인증코드, 만료시간, 생성시간 업데이트
            EmailCode emailCode = existing.get();
            emailCode.setCode(code);
            emailCode.setCreatedAt(LocalDateTime.now());
            emailCode.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            repository.save(emailCode);
        } else {
            // 신규 생성
            EmailCode emailCode = EmailCode.builder()
                    .email(email)
                    .code(code)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(5))
                    .build();
            repository.save(emailCode);
        }
    }


    @Override
    public String verifyCode(String email, String code) {
        Optional<EmailCode> emailCodeOpt = repository.findTopByEmailOrderByCreatedAtDesc(email);

        if (emailCodeOpt.isEmpty()) {
            return "invalid";
        }

        EmailCode emailCode = emailCodeOpt.get();

        if (emailCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "expired";
        }

        if (!emailCode.getCode().equals(code)) {
            return "invalid";
        }

        return "valid";
    }
}