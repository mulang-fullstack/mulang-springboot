package yoonsome.mulang.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import yoonsome.mulang.domain.user.repository.UserRepository;
import yoonsome.mulang.infra.security.CustomFailureHandler;
import yoonsome.mulang.infra.security.CustomLoginSuccessHandler;
import yoonsome.mulang.infra.security.CustomLogoutSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomFailureHandler  customFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/teacher/**").hasRole("TEACHER")
                        //.requestMatchers("/student/**").hasRole("STUDENT")
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/auth/login")              // 로그인 폼 URL (GET)
                        .loginProcessingUrl("/auth/login")     // 로그인 처리 URL (POST)
                        .usernameParameter("email")       // 파라미터 이름 변경
                        .passwordParameter("password")
                        .successHandler(customLoginSuccessHandler)
                        .failureHandler(customFailureHandler)
                        .permitAll()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/auth/login")                       // 로그인 페이지 공유
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)      // 사용자 정보 처리
                        )
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session -> session
                        .maximumSessions(10)                 // 동시 로그인 최대 수
                        .sessionRegistry(sessionRegistry())  // SessionRegistry 연결
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request,
                                              response,
                                              ex) -> response
                                .sendRedirect("/"))
                )
                .csrf(csrf -> csrf.disable()); // 초기 개발단계만

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
