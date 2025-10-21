//package yoonsome.mulang.infra.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import yoonsome.mulang.domain.user.entity.User;
//import yoonsome.mulang.domain.user.repository.UserRepository;
//
//import java.util.Collections;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google, naver
//        String nameAttributeKey = userRequest.getClientRegistration()
//                .getProviderDetails()
//                .getUserInfoEndpoint()
//                .getUserNameAttributeName();
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // provider별로 유저 정보 파싱
//        User user = saveOrUpdate(registrationId, attributes);
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
//                attributes,
//                nameAttributeKey
//        );
//    }
//
//    private User saveOrUpdate(String provider, Map<String, Object> attrs) {
//        String email = extractEmail(provider, attrs);
//        String name = extractName(provider, attrs);
//
//        return userRepository.findByEmail(email)
//                .orElseGet(() -> userRepository.save(new User(email, name, provider)));
//    }
//
//    private String extractEmail(String provider, Map<String, Object> attrs) {
//        if ("naver".equals(provider)) {
//            Map<String, Object> resp = (Map<String, Object>) attrs.get("response");
//            return (String) resp.get("email");
//        } else {
//            return (String) attrs.get("email");
//        }
//    }
//
//    private String extractName(String provider, Map<String, Object> attrs) {
//        if ("naver".equals(provider)) {
//            Map<String, Object> resp = (Map<String, Object>) attrs.get("response");
//            return (String) resp.get("name");
//        } else {
//            return (String) attrs.get("name");
//        }
//    }
//}
