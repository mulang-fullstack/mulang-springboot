package yoonsome.mulang.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * FileResourceConfig
 * ---------------------------------------------------------
 * 로컬에 저장된 업로드 파일(예: 프로필 이미지, 강의 자료 등)을
 * 브라우저에서 접근할 수 있도록 URL 경로를 매핑하는 설정 클래스입니다.
 *
 * 기존 WebSecurityConfig, AppConfig 등과 역할이 다르므로
 * 파일 리소스 전용 설정으로 이름을 FileResourceConfig 로 지정합니다.
 */
@Configuration
public class FileResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 업로드된 파일 접근 URL 패턴 지정
        registry.addResourceHandler("/upload/**")

                // 실제 파일이 저장된 로컬 경로 지정
                // Windows 예시:  C:/mulang/uploads/
                // Linux   예시:  /home/ubuntu/uploads/
                .addResourceLocations("file:///C:/YJS/mulang-springboot/upload/")

                // 브라우저 캐시 유지 시간 (초 단위) — 3600초 = 1시간
                .setCachePeriod(3600);
    }
}
