package yoonsome.mulang.api.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.student.dto.SaveResponse;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;
import yoonsome.mulang.domain.coursefavorite.repository.CourseFavoriteRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SaveServiceImpl implements SaveService {
    private final CourseFavoriteRepository courseFavoriteRepository;

    @Override
    public Page<SaveResponse> findByStudentIdWithCoursePage(Long studentId, Pageable pageable){

        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdWithCoursePage(studentId, pageable);

        return pagenations.map(SaveResponse::from);
    }

    @Override
    public Page<SaveResponse> findByStudentIdAndLanguage(Long studentId, Long languageId, Pageable pageable ){
        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdAndLanguage(studentId, languageId, pageable);

        return pagenations.map(SaveResponse::from);
    }

    @Override
    public Page<SaveResponse> findByStudentIdAndKeyword(Long studentId, String keyword, Pageable pageable ){
        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdAndKeyword(studentId, keyword , pageable);

        return pagenations.map(SaveResponse::from);
    }

    @Override
    public Page<SaveResponse> findByStudentIdAndLanguageAndKeyword(Long studentId, Long languageId, String keyword, Pageable pageable) {
        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdAndLanguageAndKeyword(studentId, languageId , keyword , pageable);

        return pagenations.map(SaveResponse::from);
    }

    @Override
    public void deleteByStudentIdAndCourseId(Long studentId, Long courseId){
        courseFavoriteRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

}
