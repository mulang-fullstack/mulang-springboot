package yoonsome.mulang.api.student.service;

import yoonsome.mulang.api.student.dto.QnaResponse;

import java.util.List;

public interface QnaService {
    //최신순
    List<QnaResponse> getQuestionPageByUserDesc(Long userId);
    //오래된순
    List<QnaResponse> getQuestionPageByUserAsc(Long userId);
}
