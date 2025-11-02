package yoonsome.mulang.api.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 언어 선택 응답 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LanguageOption {
    private String code;         // ENGLISH, JAPANESE, CHINESE
    private String name;         // 영어, 일본어, 중국어
    private String description;  // 설명
}