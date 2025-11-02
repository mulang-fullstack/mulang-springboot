package yoonsome.mulang.api.teacher.service;

import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;


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
    public void updateTeacherProfile(Long userId, TeacherProfileUpdateRequest request)
            throws IOException {

        Teacher teacher = teacherService.getTeacherByUserId(userId);
        teacher.setIntroduction(request.getIntroduction());
        teacher.setCareer(request.getCareer());

        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("유저 정보가 존재하지 않습니다.");
        }

        if (request.getNickname() != null && !request.getNickname().isBlank()) {
            var nicknameResult = UserValidator.validateNickname(request.getNickname());
            if (!nicknameResult.isValid()) {
                throw new IllegalArgumentException(nicknameResult.getMessage());
            }
            user.setNickname(request.getNickname());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        MultipartFile photo = request.getPhoto();
        // [1] 새 프로필 업로드
        if (photo != null && !photo.isEmpty()) {
            File oldFile = user.getFile();
            // ① FK 해제 후 DB 반영
            user.setFile(null);
            userService.saveUser(user);
            entityManager.flush();
            // ② 파일 삭제
            if (oldFile != null) {
                s3fileService.deleteFile(oldFile);
            }

            // ③ 새 파일 업로드 및 연결
            File savedPhoto = s3fileService.uploadProfileImage(photo);
            user.setFile(savedPhoto);
        }

        // [2] 완전 삭제 요청
        if (photo == null) {
            File oldFile = user.getFile();

            user.setFile(null);
            userService.saveUser(user);
            entityManager.flush();

            if (oldFile != null) {
                s3fileService.deleteFile(oldFile);
            }
        }
    }

}