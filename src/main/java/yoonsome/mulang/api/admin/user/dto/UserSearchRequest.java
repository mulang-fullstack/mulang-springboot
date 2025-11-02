package yoonsome.mulang.api.admin.user.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import yoonsome.mulang.domain.user.entity.User.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static yoonsome.mulang.domain.user.entity.User.Role.STUDENT;

@Data
public class UserSearchRequest {

    private Role role = STUDENT;                // STUDENT, TEACHER
    private UserStatus userStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime endDate;

    private String keyword;           // 검색어 (이름/이메일/닉네임)
    private String sortBy;
    private String sortDirection;
    private int page = 0;
    private int size = 10;
}
