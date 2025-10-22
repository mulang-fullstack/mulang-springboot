package yoonsome.mulang.api.admin.user;

import yoonsome.mulang.domain.authlog.entity.LoginLog;
import yoonsome.mulang.domain.authlog.repository.LoginLogRepository;

import java.util.List;

public class AdminUserService {
    private LoginLogRepository loginLogRepository;
    public List<LoginLog> getAllLogs() {
        List<LoginLog> list = loginLogRepository.findAll();
        System.out.println(list);
        return list;
    }
}
