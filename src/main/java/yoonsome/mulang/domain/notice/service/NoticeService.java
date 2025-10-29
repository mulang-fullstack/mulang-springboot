package yoonsome.mulang.domain.notice.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.domain.notice.dto.NoticeCreateRequest;
import yoonsome.mulang.domain.notice.dto.NoticeListResponse;
import yoonsome.mulang.domain.notice.dto.NoticeSearchRequest;
import yoonsome.mulang.api.support.dto.NoticeDetailResponse;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.user.entity.User;

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
     * 공지사항 단건 조회
     */
    NoticeDetailResponse getNoticeById(Long id);
}