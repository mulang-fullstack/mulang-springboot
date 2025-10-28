package yoonsome.mulang.api.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.student.dto.QnaResponse;
import yoonsome.mulang.domain.qna.entity.CourseQuestion;
import yoonsome.mulang.domain.qna.repository.CourseQuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {

    private final CourseQuestionRepository courseQuestionRepository;


    @Override
    public List<QnaResponse> getQuestionPageByUserDesc(Long userId) {
        List<CourseQuestion> questions = courseQuestionRepository
                .findByUser_IdOrderByCreatedAtDesc(userId);

        return questions.stream()
                .map(QnaResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<QnaResponse> getQuestionPageByUserAsc(Long userId) {
        List<CourseQuestion> questions = courseQuestionRepository
                .findByUser_IdOrderByCreatedAtAsc(userId);

        return questions.stream()
                .map(QnaResponse::from)
                .collect(Collectors.toList());
    }
}
