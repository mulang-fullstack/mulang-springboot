package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import java.time.LocalDate;

/**
 * 교사 매출 요청 DTO
 * 특정 기간 동안 교사의 강좌 매출을 조회할 때 사용
 */
@Data
public class TeacherSalesRequest {
    private Long teacherId;          // 교사 ID
    private LocalDate startDate;     // 조회 시작일
    private LocalDate endDate;       // 조회 종료일
}
