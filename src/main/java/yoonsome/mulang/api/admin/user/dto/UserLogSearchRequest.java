package yoonsome.mulang.api.admin.user.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 로그인/로그아웃 로그 검색 요청 DTO
 * - 기간, 액션, 키워드, 정렬, 페이징 정보 포함
 */
@Data
public class UserLogSearchRequest {

    private String action; // LOGIN / LOGOUT / null
    private String keyword; // 이름, 이메일 검색

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private int page = 0;
    private int size = 10;

    private String sortBy = "createdAt"; // createdAt, username
    private String sortDirection = "DESC"; // ASC or DESC
}