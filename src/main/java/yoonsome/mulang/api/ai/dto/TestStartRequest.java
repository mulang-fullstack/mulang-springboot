package yoonsome.mulang.api.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 테스트 시작 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestStartRequest {
    private String language;    // ENGLISH, JAPANESE, CHINESE
    private String level;        // A1, A2, B1, B2 (선택사항, 없으면 A1 기본)
}