package yoonsome.mulang.api.admin.user.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yoonsome.mulang.domain.user.entity.User.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserSearchRequest {

    private Role role;                // STUDENT, TEACHER, ADMIN
    private Boolean status;           // true: 활성, false: 비활성

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate= LocalDate.now()
            .withDayOfMonth(1)
            .atStartOfDay();;  // 가입 시작일

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate = LocalDate.now()
            .atTime(23, 59, 59);    // 가입 종료일

    private String keyword;           // 검색어 (이름/이메일/닉네임)
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
    private int page = 0;
    private int size = 10;
}
