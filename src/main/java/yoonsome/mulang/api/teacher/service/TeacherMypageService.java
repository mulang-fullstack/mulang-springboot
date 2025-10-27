package yoonsome.mulang.api.teacher.service;

import yoonsome.mulang.api.teacher.dto.*;
import java.io.IOException;

public interface TeacherMypageService {

    // 교사 프로필 조회
    TeacherProfileResponse getTeacherProfileResponse(Long userId);

    // 교사 프로필 수정
    void updateTeacherProfile(Long userId, TeacherProfileUpdateRequest request) throws IOException;

}
