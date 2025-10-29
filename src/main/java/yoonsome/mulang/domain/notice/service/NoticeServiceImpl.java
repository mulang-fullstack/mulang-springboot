package yoonsome.mulang.domain.notice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.domain.notice.dto.NoticeCreateRequest;
import yoonsome.mulang.domain.notice.dto.NoticeListResponse;
import yoonsome.mulang.domain.notice.dto.NoticeSearchRequest;
import yoonsome.mulang.api.support.dto.NoticeDetailResponse;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.notice.repository.NoticeRepository;
import yoonsome.mulang.domain.user.entity.User;

/**
 * 공지사항 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    @Transactional
    public Notice createNotice(NoticeCreateRequest request, User author) {

        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .type(request.getType())
                .status(request.getStatus())
                .user(author)
                .build();

        Notice saved = noticeRepository.save(notice);
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoticeListResponse> getNoticeList(NoticeSearchRequest request) {

        // 정렬 방향 결정
        Sort.Direction direction = "ASC".equalsIgnoreCase(request.getSortDirection())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        // 페이징 및 정렬 설정
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by(direction, request.getSortBy())
        );

        // 동적 쿼리 실행 (Repository에 추가 필요)
        Page<Notice> noticePage = noticeRepository.findBySearchConditions(
                request.getType(),
                request.getStatus(),
                request.getKeyword(),
                request.getStartDate(),
                request.getEndDate(),
                pageable
        );

        // DTO 변환
        return noticePage.map(NoticeListResponse::from);
    }

    @Override
    @Transactional(readOnly = true)
    public NoticeDetailResponse getNoticeById(Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("공지사항을 찾을 수 없음: id={}", id);
                    return new EntityNotFoundException("공지사항을 찾을 수 없습니다. ID: " + id);
                });
        NoticeDetailResponse response = NoticeDetailResponse.from(notice);
        log.debug("공지사항 조회 완료: id={}, title={}", id, notice.getTitle());
        return response;
    }
}