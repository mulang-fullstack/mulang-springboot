package yoonsome.mulang.user.service;

import yoonsome.mulang.user.entity.User;

public interface UserService {
    User loginUser(String email, String password);
}
