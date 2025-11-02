package yoonsome.mulang.infra.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import yoonsome.mulang.domain.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomUserDetails implements UserDetails, OAuth2User {
    private final User user;
    private Map<String, Object> attributes; // OAuth2용

    // 일반 로그인용 생성자
    public CustomUserDetails(User user) {
        this.user = user;
    }

    // OAuth2 로그인용 생성자
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // UserDetails 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getUserStatus() == User.UserStatus.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getUserStatus() == User.UserStatus.ACTIVE;
    }

    // OAuth2User 구현
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        // OAuth2에서 사용하는 이름 속성
        return user.getEmail();
    }

    // User 엔티티 접근용
    public User getUser() {
        return user;
    }
}