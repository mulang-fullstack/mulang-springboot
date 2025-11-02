package yoonsome.mulang.api.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 문제 DTO (기존 QuestionDTO 사용)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private String question;
    private List<String> choices;
    private String answer;
    private String difficulty;
    private String targetLang;
}