package yoonsome.mulang.api.teacher.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import yoonsome.mulang.domain.category.entity.Category;
import yoonsome.mulang.domain.course.entity.Course;
import yoonsome.mulang.domain.language.entity.Language;
import yoonsome.mulang.domain.teacher.entity.Teacher;
import java.util.List;

@Data
public class CourseUploadRequest {
    private String title;
    private String subtitle;
    private String content;
    private Integer price;
    private Long categoryId;
    private Long languageId;

    private MultipartFile thumbnailFile;
    private List<LectureUploadRequest> lectures;

    //임시임시 확장할때
    private Boolean status = true;
    private Integer lectureCount = 1;

    public Course toEntity(Teacher teacher, Category category, Language language, String thumbnailUrl) {
        Course course = new Course();
        course.setTeacher(teacher);
        course.setCategory(category);
        course.setLanguage(language);
        course.setTitle(this.title);
        course.setSubtitle(this.subtitle);
        course.setContent(this.content);
        course.setPrice(this.price);
        course.setStatus(this.status);
        course.setLectureCount(this.lectureCount);
        course.setThumbnail(thumbnailUrl);
        return course;
    }
}