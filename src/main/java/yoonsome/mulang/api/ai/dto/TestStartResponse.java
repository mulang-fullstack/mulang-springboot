package yoonsome.mulang.api.ai.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 테스트 시작 응답 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestStartResponse {
    private Long testId;
    private String language;
    private String level;
    private List<QuestionDTO> questions;
    private Integer totalQuestions;
}