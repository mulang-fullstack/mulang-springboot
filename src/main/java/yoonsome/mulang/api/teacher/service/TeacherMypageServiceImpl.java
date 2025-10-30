package yoonsome.mulang.api.teacher.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.teacher.dto.*;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import yoonsome.mulang.domain.teacher.service.TeacherService;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;
import yoonsome.mulang.global.util.UserValidator;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;
import yoonsome.mulang.infra.file.service.S3FileService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TeacherMypageServiceImpl implements TeacherMypageService {

    private final TeacherService teacherService;
    private final S3FileService s3fileService;
    private final UserService userService;

    // 교사 프로필 조회
    @Override
    public TeacherProfileResponse getTeacherProfileResponse(Long userId) {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        User user = userService.findById(userId);

        String profileUrl = null;
        if (user.getFile() != null) {
            File file = user.getFile();
            profileUrl = s3fileService.getPublicUrl(file.getId());
        }

        return new TeacherProfileResponse(
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                teacher.getIntroduction(),
                teacher.getCareer(),
                profileUrl
        );
    }

    // 교사 프로필 수정
    @Override
    @Transactional
    public void updateTeacherProfile(Long userId, TeacherProfileUpdateRequest request) throws IOException {
        Teacher teacher = teacherService.getTeacherByUserId(userId);
        teacher.setIntroduction(request.getIntroduction());
        teacher.setCareer(request.getCareer());

        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("유저 정보가 존재하지 않습니다.");
        }

        // 닉네임 유효성 검사 추가
        if (request.getNickname() != null && !request.getNickname().isBlank()) {
            UserValidator.ValidationResult nicknameResult = UserValidator.validateNickname(request.getNickname());
            if (!nicknameResult.isValid()) {
                throw new IllegalArgumentException(nicknameResult.getMessage());
            }
            user.setNickname(request.getNickname());
        }

        // 이메일 수정
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        // 프로필 이미지 수정
        MultipartFile photo = request.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            File savedPhoto = s3fileService.uploadProfileImage(photo);
            user.setFile(savedPhoto);
        }
    }
}