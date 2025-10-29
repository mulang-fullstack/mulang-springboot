package yoonsome.mulang.domain.notice.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.domain.notice.dto.NoticeCreateRequest;
import yoonsome.mulang.domain.notice.dto.NoticeListResponse;
import yoonsome.mulang.domain.notice.dto.NoticeSearchRequest;
import yoonsome.mulang.api.support.dto.NoticeDetailResponse;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.api.admin.system.dto.NoticeUpdateRequest;

/**
 * 공지사항 서비스 인터페이스
 */
public interface NoticeService {

    /**
     * 공지사항 생성
     */
    Notice createNotice(NoticeCreateRequest request, User author);

    /**
     * 공지사항 목록 조회 (검색 및 페이징)
     */
    Page<NoticeListResponse> getNoticeList(NoticeSearchRequest request);

    /**
     * 공지사항 상세 조회
     */
    NoticeDetailResponse getNoticeById(Long id);

    /**
     * 공지사항 수정
     */
    void updateNotice(Long id, NoticeUpdateRequest request, User user);
}