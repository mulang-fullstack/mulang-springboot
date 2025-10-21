package yoonsome.mulang.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    /**
     * 회원가입 인증 메일 발송 (HTML 템플릿 버전)
     */
    public void sendVerificationEmail(String toEmail, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("[Mulang] 회원가입 이메일 인증 안내");

            String htmlContent = """
                <div style="font-family: Pretendard, Arial, sans-serif; padding: 24px; background-color: #f9fafb;">
                    <div style="max-width: 480px; margin: auto; background: #ffffff; border-radius: 12px; padding: 32px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                        <h2 style="color: #333333; font-size: 22px; margin-bottom: 16px;">
                            ✉️ Mulang 회원가입 이메일 인증
                        </h2>
                        <p style="font-size: 15px; color: #555;">
                            안녕하세요! Mulang 서비스에 가입해주셔서 감사합니다.<br/>
                            아래 인증번호를 입력하여 회원가입을 완료해주세요.
                        </p>
                        <div style="margin: 24px 0; text-align: center;">
                            <div style="display: inline-block; font-size: 24px; letter-spacing: 4px; font-weight: 700; color: #2563eb;
                                        background: #eef2ff; padding: 16px 32px; border-radius: 8px;">
                                %s
                            </div>
                        </div>
                        <p style="font-size: 13px; color: #888;">
                            인증번호는 <strong>5분간 유효</strong>하며, 타인에게 공유하지 마세요.
                        </p>
                        <hr style="margin: 32px 0; border: none; border-top: 1px solid #eee;">
                        <p style="font-size: 12px; color: #aaa; text-align: center;">
                            ⓒ Mulang. All rights reserved.
                        </p>
                    </div>
                </div>
                """.formatted(code);

            helper.setText(htmlContent, true);
            mailSender.send(message);

            log.info("인증 메일 전송 완료 → {}", toEmail);

        } catch (MessagingException e) {
            log.error("메일 전송 실패 → {}", toEmail, e);
            throw new RuntimeException("메일 전송 중 오류 발생", e);
        }
    }
}
