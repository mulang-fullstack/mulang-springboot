package yoonsome.mulang.api.student.service;

import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import yoonsome.mulang.api.student.dto.SaveResponse;
import yoonsome.mulang.domain.coursefavorite.entity.CourseFavorite;
import yoonsome.mulang.domain.coursefavorite.repository.CourseFavoriteRepository;
import yoonsome.mulang.infra.file.service.S3FileService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class SaveServiceImpl implements SaveService {
    private final CourseFavoriteRepository courseFavoriteRepository;
    private final S3FileService s3FileService;

    @Override
    @Transactional(readOnly = true)
    public Page<SaveResponse> findByStudentIdWithCoursePage(Long studentId, Pageable pageable) {
        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdWithCoursePage(studentId, pageable);

        List<SaveResponse> responseList = pagenations.getContent().stream()
                .map(fav -> {
                    // 썸네일 URL 생성
                    String thumbnailUrl = null;
                    if (fav.getCourse().getFile() != null) {
                        thumbnailUrl = s3FileService.getPublicUrl(
                                fav.getCourse().getFile().getId()
                        );
                    }
                    // ⭐ 디버깅 로그
                    System.out.println("=== Thumbnail URL ===");
                    System.out.println("Course: " + fav.getCourse().getTitle());
                    System.out.println("File ID: " + fav.getCourse().getFile().getId());
                    System.out.println("Thumbnail URL: " + thumbnailUrl);
                    System.out.println("====================");
                    return SaveResponse.builder()
                            .id(fav.getId())
                            .course(fav.getCourse())
                            .student(fav.getStudent())
                            .createdAt(fav.getCreatedAt())
                            .thumbnailUrl(thumbnailUrl)  // ← 별도 필드로 관리
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, pagenations.getTotalElements());
    }


    @Override
    @Transactional(readOnly = true)
    public Page<SaveResponse> findByStudentIdAndLanguage(Long studentId, Long languageId, Pageable pageable ) {
        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdAndLanguage(studentId, languageId, pageable);

        List<SaveResponse> responseList = pagenations.getContent().stream()
                .map(fav -> {
                    // 썸네일 URL 생성
                    String thumbnailUrl = null;
                    if (fav.getCourse().getFile() != null) {
                        thumbnailUrl = s3FileService.getPublicUrl(
                                fav.getCourse().getFile().getId()
                        );
                    }

                    return SaveResponse.builder()
                            .id(fav.getId())
                            .course(fav.getCourse())
                            .student(fav.getStudent())
                            .createdAt(fav.getCreatedAt())
                            .thumbnailUrl(thumbnailUrl)  // ← 별도 필드로 관리
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, pagenations.getTotalElements());

    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaveResponse> findByStudentIdAndKeyword(Long studentId, String keyword, Pageable pageable ){
        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdAndKeyword(studentId, keyword , pageable);

        List<SaveResponse> responseList = pagenations.getContent().stream()
                .map(fav -> {
                    // 썸네일 URL 생성
                    String thumbnailUrl = null;
                    if (fav.getCourse().getFile() != null) {
                        thumbnailUrl = s3FileService.getPublicUrl(
                                fav.getCourse().getFile().getId()
                        );
                    }



                    return SaveResponse.builder()
                            .id(fav.getId())
                            .course(fav.getCourse())
                            .student(fav.getStudent())
                            .createdAt(fav.getCreatedAt())
                            .thumbnailUrl(thumbnailUrl)  // ← 별도 필드로 관리
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, pagenations.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaveResponse> findByStudentIdAndLanguageAndKeyword(Long studentId, Long languageId, String keyword, Pageable pageable) {
        Page<CourseFavorite> pagenations = courseFavoriteRepository
                .findByStudentIdAndLanguageAndKeyword(studentId, languageId , keyword , pageable);

        List<SaveResponse> responseList = pagenations.getContent().stream()
                .map(fav -> {
                    // 썸네일 URL 생성
                    String thumbnailUrl = null;
                    if (fav.getCourse().getFile() != null) {
                        thumbnailUrl = s3FileService.getPublicUrl(
                                fav.getCourse().getFile().getId()
                        );
                    }

                    return SaveResponse.builder()
                            .id(fav.getId())
                            .course(fav.getCourse())
                            .student(fav.getStudent())
                            .createdAt(fav.getCreatedAt())
                            .thumbnailUrl(thumbnailUrl)  // ← 별도 필드로 관리
                            .build();
                })
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, pagenations.getTotalElements());
    }

    @Override
    public void deleteByStudentIdAndCourseId(Long studentId, Long courseId){
        courseFavoriteRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

}
