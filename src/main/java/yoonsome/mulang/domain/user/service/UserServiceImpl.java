package yoonsome.mulang.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.admin.user.dto.UserSearchRequest;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.repository.UserRepository;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * 사용자 목록 검색 + 필터 + 정렬 + 페이징
     */
    @Override
    public Page<User> getUserList(UserSearchRequest request) {

        // 정렬 기준 설정
        Sort sort = Sort.by(
                "username".equals(request.getSortBy())
                        ? request.getSortDirection().equalsIgnoreCase("DESC")
                        ? Sort.Order.desc("username")
                        : Sort.Order.asc("username")
                        : request.getSortDirection().equalsIgnoreCase("ASC")
                        ? Sort.Order.asc("createdAt")
                        : Sort.Order.desc("createdAt")
        );

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        return userRepository.searchUsers(
                request.getRole(),
                request.getStatus(),
                request.getStartDate(),
                request.getEndDate(),
                request.getKeyword(),
                pageable
        );
    }
}