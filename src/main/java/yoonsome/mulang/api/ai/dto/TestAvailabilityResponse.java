package yoonsome.mulang.api.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 테스트 가능 여부 응답
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestAvailabilityResponse {

    /**
     * 테스트 가능 여부
     */
    private boolean available;

    /**
     * 메시지
     */
    private String message;

    /**
     * 마지막 테스트 날짜
     */
    private LocalDateTime lastTestDate;

    /**
     * 남은 일수
     */
    private Long daysRemaining;

    /**
     * 다음 가능 날짜
     */
    private LocalDateTime nextAvailableDate;
}