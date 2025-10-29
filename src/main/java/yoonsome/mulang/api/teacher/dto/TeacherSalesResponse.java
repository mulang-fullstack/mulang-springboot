package yoonsome.mulang.api.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 강좌 단위 매출 응답 DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSalesResponse {
    private Long courseId;           // 강좌 ID
    private String courseTitle;      // 강좌명
    private Integer totalAmount;     // 총 매출액 (COMPLETED 결제 합계)
    private Integer totalCount;      // 결제 건수
}
