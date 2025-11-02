package yoonsome.mulang.infra.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;
import yoonsome.mulang.domain.log.entity.UserLog;
import yoonsome.mulang.domain.log.repository.UserLogRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionCleanupListener implements ApplicationListener<SessionDestroyedEvent> {

    private final UserLogRepository userLogRepository;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        try {
            event.getSecurityContexts().forEach(context -> {
                Object principal = context.getAuthentication().getPrincipal();

                if (principal instanceof CustomUserDetails userDetails) {
                    UserLog log = UserLog.builder()
                            .email(userDetails.getUser().getEmail())
                            .username(userDetails.getUser().getUsername())
                            .action(UserLog.ActionType.LOGOUT) // 자동 만료도 LOGOUT으로 기록
                            .ip("SESSION_TIMEOUT")
                            .userAgent("Session Expired")
                            .build();

                    userLogRepository.save(log);
                }
            });
        } catch (Exception e) {
            log.error("❌ 세션 만료 로그 기록 실패", e);
        }
    }
}