package yoonsome.mulang.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoonsome.mulang.api.admin.system.dto.NoticeCreateRequest;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.notice.repository.NoticeRepository;
import yoonsome.mulang.domain.user.entity.User;

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

        return noticeRepository.save(notice);
    }



}

