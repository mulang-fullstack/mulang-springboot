package yoonsome.mulang.domain.notice.service;

import yoonsome.mulang.api.admin.system.dto.NoticeCreateRequest;
import yoonsome.mulang.domain.notice.entity.Notice;
import yoonsome.mulang.domain.user.entity.User;

public interface NoticeService {
    Notice createNotice(NoticeCreateRequest request, User author);
}
