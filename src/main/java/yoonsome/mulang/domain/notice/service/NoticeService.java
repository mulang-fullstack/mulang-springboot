package yoonsome.mulang.domain.notice.service;

import org.springframework.data.domain.Page;
import yoonsome.mulang.api.admin.system.dto.NoticeCreateRequest;
import yoonsome.mulang.api.admin.system.dto.NoticeListResponse;
import yoonsome.mulang.api.admin.system.dto.NoticeSearchRequest;
import yoonsome.mulang.api.admin.system.dto.NoticeUpdateRequest;
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
    Notice getNoticeById(Long id);

    /**
     * 공지사항 수정
     */
    Notice updateNotice(Long id, NoticeUpdateRequest request, User modifier);

    /**
     * 공지사항 삭제
     */
    void deleteNotice(Long id, User user);
}