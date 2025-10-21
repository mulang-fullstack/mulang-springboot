package yoonsome.mulang.api.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 페이징 정보 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private List<T> content;      // 페이지 데이터
    private int currentPage;      // 현재 페이지 번호
    private int totalPages;       // 전체 페이지 수
    private long totalCount;      // 전체 데이터 수
    private int size;            // 페이지 크기
    private boolean hasNext;     // 다음 페이지 존재 여부
    private boolean hasPrevious; // 이전 페이지 존재 여부
}