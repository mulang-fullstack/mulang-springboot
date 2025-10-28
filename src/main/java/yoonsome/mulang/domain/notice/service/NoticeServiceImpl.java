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
import yoonsome.mulang.api.admin.system.dto.NoticeCreateRequest;
import yoonsome.mulang.api.admin.system.dto.NoticeListResponse;
import yoonsome.mulang.api.admin.system.dto.NoticeSearchRequest;
import yoonsome.mulang.api.admin.system.dto.NoticeUpdateRequest;
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
        log.info("공지사항 생성: title={}, type={}, author={}",
                request.getTitle(), request.getType(), author.getNickname());

        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .type(request.getType())
                .status(request.getStatus())
                .user(author)
                .build();

        Notice saved = noticeRepository.save(notice);
        log.info("공지사항 생성 완료: id={}", saved.getId());

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoticeListResponse> getNoticeList(NoticeSearchRequest request) {
        log.debug("공지사항 목록 조회: page={}, size={}, keyword={}",
                request.getPage(), request.getSize(), request.getKeyword());

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
    public Notice getNoticeById(Long id) {
        log.debug("공지사항 조회: id={}", id);

        return noticeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("공지사항을 찾을 수 없음: id={}", id);
                    return new EntityNotFoundException("공지사항을 찾을 수 없습니다. ID: " + id);
                });
    }

    @Override
    @Transactional
    public Notice updateNotice(Long id, NoticeUpdateRequest request, User modifier) {
        log.info("공지사항 수정: id={}, modifier={}", id, modifier.getNickname());

        Notice notice = getNoticeById(id);

        // 엔티티 수정
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setType(request.getType());
        notice.setStatus(request.getStatus());

        Notice updated = noticeRepository.save(notice);
        log.info("공지사항 수정 완료: id={}", updated.getId());

        return updated;
    }

    @Override
    @Transactional
    public void deleteNotice(Long id, User user) {
        log.info("공지사항 삭제: id={}, user={}", id, user.getNickname());

        // 존재 여부 확인
        Notice notice = getNoticeById(id);

        noticeRepository.delete(notice);
        log.info("공지사항 삭제 완료: id={}", id);
    }
}