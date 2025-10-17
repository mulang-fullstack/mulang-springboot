package yoonsome.mulang.course.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import yoonsome.mulang.course.entity.Category;
import yoonsome.mulang.course.entity.Course;
import yoonsome.mulang.course.entity.CourseType;
import yoonsome.mulang.course.entity.Language;
import yoonsome.mulang.course.repository.CourseRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Language javaLang;
    private Language pythonLang;
    private Category webCategory;
    private Category aiCategory;

    private Course course1;
    private Course course2;
    private Course course3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 샘플 Language & Category
        javaLang = new Language();
        javaLang.setId(1L);
        javaLang.setName("Java");

        pythonLang = new Language();
        pythonLang.setId(2L);
        pythonLang.setName("Python");

        webCategory = new Category();
        webCategory.setId(1L);
        webCategory.setName("Web");

        aiCategory = new Category();
        aiCategory.setId(2L);
        aiCategory.setName("AI");

        // 샘플 Course 3개
        course1 = new Course(1L, "Java 기초", "Java 입문 과정",
                LocalDate.now(), LocalDate.now().plusDays(7),
                LocalDate.now().plusDays(8), LocalDate.now().plusDays(30),
                true, 30, 10, 100000, CourseType.VOD, 5, javaLang, webCategory);

        course2 = new Course(2L, "Python 기초", "Python 입문 과정",
                LocalDate.now(), LocalDate.now().plusDays(7),
                LocalDate.now().plusDays(8), LocalDate.now().plusDays(30),
                true, 25, 15, 120000, CourseType.ONLINE, 8, pythonLang, aiCategory);

        course3 = new Course(3L, "Spring Boot 심화", "Spring Boot 실무 과정",
                LocalDate.now(), LocalDate.now().plusDays(7),
                LocalDate.now().plusDays(8), LocalDate.now().plusDays(30),
                true, 20, 5, 150000, CourseType.OFFLINE, 10, javaLang, webCategory);
    }

    @Test
    void testGetCourseListByLanguage() {
        Page<Course> page = new PageImpl<>(Arrays.asList(course1, course3));

        when(courseRepository.findByLanguageId(1L, PageRequest.of(0, 10))).thenReturn(page);

        Page<Course> result = courseService.getCourseListByLanguage(1L, PageRequest.of(0, 10));

        System.out.println("=== getCourseListByLanguage ===");
        result.getContent().forEach(c -> System.out.println(c.getTitle()));

        assertEquals(2, result.getContent().size());
        verify(courseRepository, times(1)).findByLanguageId(1L, PageRequest.of(0, 10));
    }

    @Test
    void testGetCourseListByCategory() {
        Page<Course> page = new PageImpl<>(Arrays.asList(course1, course3));

        when(courseRepository.findByCategoryId(1L, PageRequest.of(0, 10))).thenReturn(page);

        Page<Course> result = courseService.getCourseListByCategory(1L, PageRequest.of(0, 10));

        System.out.println("=== getCourseListByCategory ===");
        result.getContent().forEach(c -> System.out.println(c.getTitle()));

        assertEquals(2, result.getContent().size());
        verify(courseRepository, times(1)).findByCategoryId(1L, PageRequest.of(0, 10));
    }

    @Test
    void testGetCourseDetail() {
        when(courseRepository.findById(2L)).thenReturn(Optional.of(course2));

        Course result = courseService.getCourseDetail(2L);

        System.out.println("=== getCourseDetail ===");
        System.out.println("result: "+result);
        //System.out.println(result.getTitle() + " / " + result.getLanguage().getName());

        assertNotNull(result);
        assertEquals(2L, result.getId());
    }

    @Test
    void testDeleteCourse() {
        doNothing().when(courseRepository).deleteById(3L);

        courseService.deleteCourse(3L);

        System.out.println("=== deleteCourse ===");
        System.out.println("Deleted course with ID: 3");

        verify(courseRepository, times(1)).deleteById(3L);
    }
}