package yoonsome.mulang.course.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.repository.CourseRepository;
import yoonsome.mulang.file.entity.File;
import yoonsome.mulang.file.service.FileService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private final CourseRepository courseRepository;
    @Autowired
    private LectureService lectureService;
    @Autowired
    private final FileService fileService;

    @Override
    public Page<Course> getCourseListByLanguage(Long languageId, Pageable pageable) {
        return courseRepository.findByLanguageId(languageId, pageable);
    }

    @Override
    public Page<Course> getCourseListByCategory(Long categoryId, Pageable pageable) {
        return courseRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Course getCourseDetail(long id) {
        Optional<Course> optCourse = courseRepository.findById(id);
        if (optCourse.isPresent()) {
            Course course = optCourse.get();
            return course;
        }else return null;
    }

    @Override
    public Course registerCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void modifyCourse(Course course) {
        Optional<Course> optCourse = courseRepository.findById(course.getId());
        if (optCourse.isPresent()) {
            Course originCourse = optCourse.get();
            originCourse.setPrice(course.getPrice());//수정 항목 나중에 양진석가모니 완성하면 추가할거임
        }
    }

    @Override
    public void deleteCourse(long id) {
        courseRepository.deleteById(id);
    }
    /**
     * 강좌를 저장하고, 전달받은 리스트를 이용해 여러 개의 강의를 생성
     */
    public void createCourseWithLectures(
            Course course,
            List<String> lectureTitles,
            List<MultipartFile> lectureVideos,
            MultipartFile thumbnail
    ) throws IOException {
        if (thumbnail != null && !thumbnail.isEmpty()) {
            File savedThumbnail = fileService.createFile(thumbnail);
            course.setThumbnail(savedThumbnail.getPath());
        }

        Course savedCourse = courseRepository.save(course);

        if (lectureTitles == null || lectureTitles.isEmpty()) {
            return;
        }
        for (int i = 0; i < lectureTitles.size(); i++) {
            String title = lectureTitles.get(i);

            MultipartFile video = null;
            if (lectureVideos != null && lectureVideos.size() > i) {
                video = lectureVideos.get(i);
            }
            lectureService.createLectureWithFile(title, savedCourse, video);
        }
    }

}
