package yoonsome.mulang.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;
import yoonsome.mulang.infra.security.CustomUserDetails;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        User user = saveOrUpdate(registrationId, attributes);

        return new CustomUserDetails(user, attributes);
    }

    private User saveOrUpdate(String provider, Map<String, Object> attrs) {
        String email = extractEmail(provider, attrs);
        String name = extractName(provider, attrs);

        return userRepository.findByEmail(email)
                .map(existingUser -> existingUser)
                .orElseGet(() -> {
                    String nickname = generateUniqueNickname(name);
                    // 소셜 로그인용 더미 비밀번호 (인코딩 없이 저장)
                    // 어차피 소셜 로그인은 비밀번호를 사용하지 않음
                    String dummyPassword = "{noop}" + UUID.randomUUID().toString();

                    return userRepository.save(
                            User.builder()
                                    .email(email)
                                    .username(name)
                                    .nickname(nickname)
                                    .password(dummyPassword)
                                    .provider(provider)
                                    .role(User.Role.STUDENT)
                                    .build()
                    );
                });
    }

    private String generateUniqueNickname(String baseName) {
        String nickname = baseName;
        int suffix = 1;

        while (userRepository.existsByNickname(nickname)) {
            nickname = baseName + suffix++;
        }

        return nickname;
    }

    private String extractEmail(String provider, Map<String, Object> attrs) {
        if ("naver".equals(provider)) {
            Map<String, Object> resp = (Map<String, Object>) attrs.get("response");
            return (String) resp.get("email");
        } else if ("google".equals(provider)) {
            return (String) attrs.get("email");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attrs.get("kakao_account");
            return (String) kakaoAccount.get("email");
        }
        return (String) attrs.get("email");
    }

    private String extractName(String provider, Map<String, Object> attrs) {
        if ("naver".equals(provider)) {
            Map<String, Object> resp = (Map<String, Object>) attrs.get("response");
            return (String) resp.get("name");
        } else if ("google".equals(provider)) {
            return (String) attrs.get("name");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attrs.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            return (String) profile.get("nickname");
        }
        return (String) attrs.get("name");
    }
}