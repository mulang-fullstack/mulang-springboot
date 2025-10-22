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

    @Override
    @Transactional
    public void saveCode(String email, String code) {
        Optional<EmailCode> existing = repository.findByEmail(email);

        if (existing.isPresent()) {
            // 기존 레코드 → 인증코드, 만료시간, 생성시간 업데이트
            EmailCode emailCode = existing.get();
            emailCode.setCode(code);
            emailCode.setVerified(false); // 새 코드 발급 시 인증 상태 초기화
            emailCode.setCreatedAt(LocalDateTime.now());
            emailCode.setExpiresAt(LocalDateTime.now().plusMinutes(5));
            repository.save(emailCode);
        } else {
            // 신규 생성
            EmailCode emailCode = EmailCode.builder()
                    .email(email)
                    .code(code)
                    .verified(false) // 초기 인증 상태는 false
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(5))
                    .build();
            repository.save(emailCode);
        }
    }

    @Override
    @Transactional
    public String verifyCode(String email, String code) {
        Optional<EmailCode> emailCodeOpt = repository.findTopByEmailOrderByCreatedAtDesc(email);

        if (emailCodeOpt.isEmpty()) {
            return "invalid";
        }

        EmailCode emailCode = emailCodeOpt.get();

        // 1. 만료 여부 확인
        if (emailCode.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "expired";
        }

        // 2. 코드 일치 여부 확인
        if (!emailCode.getCode().equals(code)) {
            return "invalid";
        }

        // 3. 검증 성공 시 verified 플래그를 true로 설정
        emailCode.setVerified(true);
        repository.save(emailCode);

        return "valid";
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isVerified(String email) {
        Optional<EmailCode> emailCodeOpt = repository.findTopByEmailOrderByCreatedAtDesc(email);

        if (emailCodeOpt.isEmpty()) {
            return false;
        }

        EmailCode emailCode = emailCodeOpt.get();

        // 인증 완료 여부와 만료되지 않았는지 함께 확인
        return emailCode.getVerified() && !emailCode.isExpired();
    }

    @Override
    @Transactional
    public void removeVerified(String email) {
        Optional<EmailCode> emailCodeOpt = repository.findByEmail(email);

        // 해당 이메일의 인증 정보가 있으면 삭제
        emailCodeOpt.ifPresent(repository::delete);
    }
}