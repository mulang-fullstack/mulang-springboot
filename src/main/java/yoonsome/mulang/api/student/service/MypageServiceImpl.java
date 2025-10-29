package yoonsome.mulang.api.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.api.student.dto.MypageResponse;
import yoonsome.mulang.domain.user.entity.User;
import yoonsome.mulang.domain.user.service.UserService;
import yoonsome.mulang.infra.file.entity.File;
import yoonsome.mulang.infra.file.service.FileService;

import java.io.IOException;

@Service
@RequiredArgsConstructor //
public class MypageServiceImpl implements MypageService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Override
    public MypageResponse getUserInfo(Long userid) {
            
        User user = userService.findById(userid); // userservice로 db조회

        MypageResponse userSearchInfoDTO = new MypageResponse();
        userSearchInfoDTO.setId(user.getId());
        userSearchInfoDTO.setNickname(user.getNickname());
        userSearchInfoDTO.setUsername(user.getUsername());
        userSearchInfoDTO.setEmail(user.getEmail());
        // 이 부분 추가!
        if (user.getFile() != null) {
            userSearchInfoDTO.setPhotoUrl(user.getFile().getUrl());
        }
        return userSearchInfoDTO;
    }

    public boolean verifyPassword(String password, String realpassword) {
        return passwordEncoder.matches(password, realpassword);
    }
    @Transactional
    public void updateUserInfo(Long userid, String email, String nickname, MultipartFile photo) {
        User user = userService.findById(userid);

        user.setNickname(nickname);
        user.setEmail(email);

        if (photo != null && !photo.isEmpty()) {
            // 기존 파일 있으면 삭제
            if (user.getFile() != null) {
                fileService.deleteFile(user.getFile());
            }

            // 새 파일 저장
            try {
                File savedPhoto = fileService.createFile(photo);
                user.setFile(savedPhoto);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }
    }


    @Transactional
    public void updatepassword(Long userid,String password){
        User user = userService.findById(userid);
        user.setPassword(passwordEncoder.encode(password));
    }



}
