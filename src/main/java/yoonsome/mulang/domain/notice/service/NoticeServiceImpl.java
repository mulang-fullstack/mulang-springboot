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
import yoonsome.mulang.api.admin.system.dto.NoticeUpdateRequest;
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

    @Override
    @Transactional
    public void updateNotice(Long id, NoticeUpdateRequest request, User user) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("공지사항을 찾을 수 없음: id={}", id);
                    return new EntityNotFoundException("공지사항을 찾을 수 없습니다. ID: " + id);
                });

        // 권한 확인: 작성자 본인 또는 관리자만 수정 가능
        if (!notice.getUser().getId().equals(user.getId()) && !isAdmin(user)) {
            log.warn("공지사항 수정 권한 없음: userId={}, noticeId={}", user.getId(), id);
            throw new IllegalStateException("공지사항을 수정할 권한이 없습니다.");
        }

        // 엔티티 업데이트
        notice.update(
                request.getType(),
                request.getTitle(),
                request.getContent(),
                request.getStatus()
        );

        log.info("공지사항 수정 완료 - ID: {}, 제목: {}", notice.getId(), notice.getTitle());
    }

    /**
     * 관리자 권한 확인
     * User 엔티티에 isAdmin() 메서드가 없는 경우 여기서 처리
     */
    private boolean isAdmin(User user) {
        // User 엔티티에 isAdmin() 메서드가 있다면 그것을 사용
        // return user.isAdmin();

        // 또는 Role을 직접 체크
        return user.getRole() != null &&
                user.getRole().name().equals("ADMIN");
    }
}